package com.rappi.technicalexercise.ui.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.rappi.technicalexercise.ui.common.Scope

abstract class ScopedViewModel : ViewModel(), Scope by Scope.Impl() {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}