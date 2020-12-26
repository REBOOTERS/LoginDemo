package com.engineer.android.mini.ui.pure

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.engineer.android.mini.R
import com.engineer.android.mini.ext.dp
import com.engineer.android.mini.ext.getStatusBarHeight
import com.engineer.android.mini.ext.resizeMarginTop
import com.engineer.android.mini.ext.toast
import com.engineer.android.mini.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main_content.*


@SuppressLint("SetTextI18n")
class PureUIActivity : BaseActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pure_ui)
        setUpUi()

        TransitionManager.beginDelayedTransition(root_content)

        image_view.setOnClickListener {
            // Transition 动画 https://github.com/xiaweizi/TransitionDemo
            val changeBounds = ChangeBounds()
            changeBounds.interpolator = LinearInterpolator()
            TransitionManager.beginDelayedTransition(root_content, changeBounds)
            val params = image_view.layoutParams
            val hw: Float = image_view.measuredWidth * 1.0f / image_view.measuredHeight
            if (image_view.measuredWidth >= (resources.displayMetrics.widthPixels - 20.dp)) {
                params.width = resources.displayMetrics.widthPixels / 2
                params.height = ((resources.displayMetrics.widthPixels / 2) * (1 / hw)).toInt()
            } else {
                params.width = resources.displayMetrics.widthPixels - 20.dp
                params.height = ((resources.displayMetrics.widthPixels - 20.dp) * (1 / hw)).toInt()
            }
            image_view.layoutParams = params
        }

        layout_ac.setOnClickListener {
            gotoPage(LayoutActivity::class.java)
        }


        image_view.resizeMarginTop(getStatusBarHeight())
    }



    private fun setUpUi() {
        systemDayNight()
        full_screen.setOnClickListener { gotoPage(FullscreenActivity::class.java) }
    }

    /**
     * https://blog.csdn.net/guolin_blog/article/details/106061657
     *
     * Android 10 日夜间模式
     */
    private fun systemDayNight() {
        val modeStr = if (isNightMode()) "夜间" else "日间"
        val current = AppCompatDelegate.getDefaultNightMode()
        current_theme.text = "当前日夜间模式 : $modeStr,mode = $current"
        when (current) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> follow_system.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> force_dark.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> force_light.isChecked = true
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY -> auto_battery.isChecked = true
            else -> {
            }
        }
        theme_radio.setOnCheckedChangeListener { _, checkedId ->
            var mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            when (checkedId) {
                R.id.follow_system -> mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                R.id.force_dark -> mode = AppCompatDelegate.MODE_NIGHT_YES
                R.id.force_light -> mode = AppCompatDelegate.MODE_NIGHT_NO
                R.id.auto_battery -> mode = AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentMode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                "夜间模式 On".toast()
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                "夜间模式 Off".toast()
            }
        }
    }


}
