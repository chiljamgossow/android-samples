package com.expertlead.expertleadtest.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.expertlead.expertleadtest.ExpertLeadApplication
import com.expertlead.expertleadtest.data.LoginGateway
import com.expertlead.expertleadtest.data.LoginService
import com.expertlead.expertleadtest.domain.LoginUseCase
import com.expertlead.expertleadtest.ui.login.presenter.LoginPresenter
import com.expertlead.expertleadtest.ui.success.presenter.SuccessPresenter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.experimental.CoroutineContext

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val kodein: DKodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginPresenter::class.java -> LoginPresenter(
                    kodein.instance()
            )
            SuccessPresenter::class.java -> SuccessPresenter()
            else -> throw IllegalAccessException("Unable to fetchUsage ${modelClass.name}")
        } as T
    }
}

class AppModule private constructor() {

    companion object {

        private const val RESULT_CONTEXT = "resultContext"
        private const val WORK_CONTEXT = "workContext"
        private const val LOGGING = "logging"

        @JvmStatic
        @JvmName("create")
        operator fun invoke() = Kodein.Module("FormModule") {
            bind<OkHttpClient>() with singleton {
                return@singleton OkHttpClient.Builder().addInterceptor(instance(LOGGING)).build()
            }

            bind<Interceptor>(tag = LOGGING) with singleton {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                return@singleton httpLoggingInterceptor
            }

            bind<LoginService>() with provider {
                Retrofit.Builder()
                        .baseUrl("https://kzkucb84m5.execute-api.us-east-1.amazonaws.com/test/")
                        .client(instance())
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .addConverterFactory(MoshiConverterFactory.create())
                        .build().create(LoginService::class.java)
            }

            bind<LoginGateway>() with provider { LoginGateway(instance()) }

            bind<CoroutineContext>(WORK_CONTEXT) with singleton { CommonPool }
            bind<CoroutineContext>(RESULT_CONTEXT) with singleton { UI }

            bind<LoginUseCase>() with provider {
                LoginUseCase(instance(), instance(WORK_CONTEXT), instance(RESULT_CONTEXT))
            }
        }
    }
}