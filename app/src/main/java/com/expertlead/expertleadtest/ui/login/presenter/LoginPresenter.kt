package com.expertlead.expertleadtest.ui.login.presenter

import com.expertlead.expertleadtest.domain.LoginResult
import com.expertlead.expertleadtest.domain.LoginUseCase
import com.expertlead.expertleadtest.ui.base.presenter.BasePresenter
import com.expertlead.expertleadtest.ui.login.LoginContract

class LoginPresenter(private val loginUseCase: LoginUseCase) :
        BasePresenter<LoginContract.LoginMvpView>(), LoginContract.LoginInteractionListener {

    override var password: String = ""
        set(value) {
            field = value
            updateButtonState()
            if (isPasswordValid) mvpView?.setPasswordValid() else mvpView?.showPasswordError()
        }

    override var email: String = ""
        set(value) {
            field = value
            updateButtonState()
            if (isEmailValid) mvpView?.setEmailValid() else mvpView?.showEmailError()
        }

    override fun onButtonClick() {
        synchronized(isResultPending) {
            if (isResultPending) {
                isResultPending = false
                loginUseCase.cancel()
                mvpView?.let {
                    it.hideLoading()
                    it.setButtonLabelRetry()
                }
            } else {
                isResultPending = true
                mvpView?.showLoading()
                mvpView?.setButtonLabelCancel()
                loginUseCase.login(email, password) { onLoginResult(it) }
            }
        }
    }

    private fun onLoginResult(result: LoginResult) {
        synchronized(isResultPending) {
            isResultPending = false
            mvpView?.hideLoading()
            when (result) {
                is LoginResult.Success -> mvpView?.showSuccessScreen()
                is LoginResult.Failure -> {
                    mvpView?.let {
                        it.showErrorMessage(result.message)
                        it.setButtonLabelRetry()
                    }
                }
                LoginResult.Cancelled -> mvpView?.setButtonLabelRetry()
            }
        }
    }

    private fun updateButtonState() {
        if (isEmailValid && isPasswordValid) mvpView?.enableButton()
        else mvpView?.disableButton()
    }

    private val isEmailValid
        get() = email.contains('@')

    private val isPasswordValid
        get() = password.isNotEmpty()

    private var isResultPending = false
}
