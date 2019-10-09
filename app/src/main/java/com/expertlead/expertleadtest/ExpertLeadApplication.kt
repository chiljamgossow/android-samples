package com.expertlead.expertleadtest

import android.app.Application
import com.expertlead.expertleadtest.data.LoginGateway
import com.expertlead.expertleadtest.data.LoginService
import com.expertlead.expertleadtest.domain.LoginUseCase
import com.expertlead.expertleadtest.ui.AppModule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.experimental.CoroutineContext

class ExpertLeadApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@ExpertLeadApplication))
        import(AppModule())
    }
}
