package com.expertlead.expertleadtest.domain

import com.expertlead.expertleadtest.data.LoginGateway
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class LoginUseCase(
        private val loginGateway: LoginGateway,
        private val workContext: CoroutineContext,
        private val resultContext: CoroutineContext) {

    fun login(email: String, password: String, onResult: (LoginResult) -> Unit) {
        cancel()
        job = async(workContext) { loginGateway.login(email, password) }
        launch(resultContext) { onResult(job?.await() ?: LoginResult.Cancelled) }
    }

    fun cancel() {
        job?.cancel()
        job = null
    }

    private var job : Deferred<LoginResult>? = null
}

sealed class LoginResult {
    data class Success(val token: String, val message: String) : LoginResult()
    data class Failure(val message: String) : LoginResult()
    object Cancelled: LoginResult()
}