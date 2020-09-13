package com.engineer.android.mini.jetpack.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Created on 2020/9/13.
 * @author rookie
 */
class FooViewModel(application: Application) : AndroidViewModel(application) {
    internal val foo = MutableLiveData<Int>()

    fun getFoo(): MutableLiveData<Int> {
        return foo
    }

    fun doFoo() {
        Handler(Looper.getMainLooper()).postDelayed({
            foo.value = 100
        }, 2000)
    }

    fun doFoo2() {
        Handler().postDelayed({
            foo.value = 2000
        }, 3000)
    }
}