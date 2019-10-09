package com.expertlead.expertleadtest.ui.base.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import com.expertlead.expertleadtest.ui.base.BaseContract

abstract class BasePresenter<V : BaseContract.BaseMvpView> : BaseContract.BaseInteractionListener<V>, LifecycleObserver, ViewModel() {

    protected var mvpView: V? = null

    override fun onViewCreated(mvpView: V, viewLifecycle: Lifecycle) {
        this.mvpView = mvpView
        viewLifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroy() {
        mvpView = null
    }
}
