package com.expertlead.expertleadtest.ui.login.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.EditorInfo
import com.expertlead.expertleadtest.R
import com.expertlead.expertleadtest.databinding.ActivityLoginBinding
import com.expertlead.expertleadtest.ui.ViewModelFactory
import com.expertlead.expertleadtest.ui.base.BaseContract
import com.expertlead.expertleadtest.ui.base.view.BaseActivity
import com.expertlead.expertleadtest.ui.login.LoginContract
import com.expertlead.expertleadtest.ui.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class LoginActivity : BaseActivity(), LoginContract.LoginMvpView, KodeinAware {

    private val _parentKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein)
        bind<LoginContract.LoginInteractionListener>() with provider {
            ViewModelProviders.of(this@LoginActivity, ViewModelFactory(kodein.direct))
                    .get(LoginPresenter::class.java)
        }
        bind<LoginViewModel>() with provider {
            ViewModelProviders.of(this@LoginActivity).get(LoginViewModel::class.java)
        }
    }

    private val loginPresenter: LoginContract.LoginInteractionListener by instance()
    private val loginViewModel: LoginViewModel by instance()

    override var presenter: BaseContract.BaseInteractionListener<BaseContract.BaseMvpView>? = null
        get() = loginPresenter as BaseContract.BaseInteractionListener<BaseContract.BaseMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            setLifecycleOwner(this@LoginActivity)
            credentials = loginViewModel
            clickListener = View.OnClickListener {
                loginPresenter.onButtonClick()
            }
        }

        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    loginPresenter.onButtonClick()
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }

        loginViewModel.emailLiveData.observe(this@LoginActivity, Observer { value ->
            loginPresenter.email = value?: "" })

        loginViewModel.passwordLiveData.observe(this@LoginActivity, Observer { value ->
            loginPresenter.password = value?: "" })
    }

    override fun enableButton() {
        loginButton.isEnabled = true
    }

    override fun disableButton() {
        loginButton.isEnabled = false
    }

    override fun setButtonLabelCancel() {
        loginButton.text = getString(R.string.cancel)
    }

    override fun setButtonLabelRetry() {
        loginButton.text = getString(R.string.retry)
    }

    override fun showLoading() {
        progressBarLogin.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBarLogin.visibility = View.INVISIBLE
    }

    override fun showErrorMessage(message: String) {
        Snackbar.make(rootLayoutLogin, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showPasswordError() {
        passwordLayout.error = "This field cannot be empty"
    }

    override fun showEmailError() {
        emailLayout.error = "This is not a valid email"
    }

    override fun setPasswordValid() {
        passwordLayout.error = null
    }

    override fun setEmailValid() {
        emailLayout.error = null
    }
}

class LoginViewModel : ViewModel() {
        val emailLiveData = MutableLiveData<String>()
        val passwordLiveData = MutableLiveData<String>()
}