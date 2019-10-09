package com.expertlead.expertleadtest.ui.success.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle

import com.expertlead.expertleadtest.R
import com.expertlead.expertleadtest.ui.ViewModelFactory
import com.expertlead.expertleadtest.ui.base.BaseContract
import com.expertlead.expertleadtest.ui.base.view.BaseActivity
import com.expertlead.expertleadtest.ui.login.presenter.LoginPresenter
import com.expertlead.expertleadtest.ui.success.SuccessContract
import com.expertlead.expertleadtest.ui.success.presenter.SuccessPresenter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class SuccessActivity : BaseActivity(), SuccessContract.SuccessMvpView, KodeinAware {

    private val _parentKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein)
        bind<SuccessContract.SuccessInteractionListener>() with provider {
            ViewModelProviders.of(this@SuccessActivity, ViewModelFactory(kodein.direct))
                    .get(SuccessPresenter::class.java)
        }
    }

    private val successPresenter: SuccessContract.SuccessInteractionListener by instance()

    override var presenter: BaseContract.BaseInteractionListener<BaseContract.BaseMvpView>? = null
        get() = successPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
