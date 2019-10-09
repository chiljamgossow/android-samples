package com.expertlead.expertleadtest.ui.base

import android.arch.lifecycle.Lifecycle

interface BaseContract {

    interface BaseMvpView {
        fun showSuccessScreen()
    }

    interface BaseInteractionListener<V : BaseMvpView> {
        fun onViewCreated(mvpView: V, viewLifecycle: Lifecycle)
    }
}
