package com.expertlead.expertleadtest.ui.base.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.expertlead.expertleadtest.ui.base.BaseContract
import com.expertlead.expertleadtest.ui.success.view.SuccessActivity


abstract class BaseActivity : AppCompatActivity(), BaseContract.BaseMvpView {

    protected abstract var presenter: BaseContract.BaseInteractionListener<BaseContract.BaseMvpView>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter?.onViewCreated(this, this.lifecycle)
    }

    override fun showSuccessScreen() {
        val intent = Intent(this, SuccessActivity::class.java)
        startActivity(intent)
    }
}
