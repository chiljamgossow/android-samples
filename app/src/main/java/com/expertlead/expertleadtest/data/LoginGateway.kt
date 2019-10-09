package com.expertlead.expertleadtest.data

import com.expertlead.expertleadtest.domain.LoginResult
import kotlinx.coroutines.experimental.Deferred
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST
import java.net.HttpURLConnection

class LoginGateway(private val loginService: LoginService){
    suspend fun login(email: String, password: String): LoginResult {
        return try {
            val deferred = loginService.authenticateWithEmail(Credentials(email, password))
            return deferred.await()
        } catch (httpException: HttpException) {
            when (httpException.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED ->
                    LoginResult.Failure(httpException.getErrorMessage())
                else -> LoginResult.Failure("Something went wrong")
            }
        } catch (throwable: Throwable) {
            LoginResult.Failure("Something went wrong")
        }
    }
}

fun HttpException.getErrorMessage(): String {
    response().errorBody()?.string()?.let {
        return JSONObject(it).getString("message")
    }
    return "unknown"
}

data class Credentials(val email: String, val password: String)

interface LoginService {
    @POST("authenticate")
    fun authenticateWithEmail(@Body credentials: Credentials): Deferred<LoginResult.Success>
}
