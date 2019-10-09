package com.expertlead.expertleadtest.ui.login

import com.expertlead.expertleadtest.ui.base.BaseContract

interface LoginContract {

    interface LoginMvpView : BaseContract.BaseMvpView {
        fun enableButton()
        fun disableButton()
        fun setButtonLabelCancel()
        fun setButtonLabelRetry()
        fun showLoading()
        fun hideLoading()
        fun showErrorMessage(message: String)
        fun showPasswordError()
        fun showEmailError()
        fun setPasswordValid()
        fun setEmailValid()
    }

    interface LoginInteractionListener : BaseContract.BaseInteractionListener<LoginContract.LoginMvpView> {
        var email : String
        var password : String
        fun onButtonClick()
    }
}
