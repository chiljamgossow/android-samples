package com.expertlead.expertleadtest.ui.success

import com.expertlead.expertleadtest.ui.base.BaseContract

interface SuccessContract {

    interface SuccessMvpView : BaseContract.BaseMvpView

    interface SuccessInteractionListener : BaseContract.BaseInteractionListener<BaseContract.BaseMvpView>
}
