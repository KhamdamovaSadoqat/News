package com.example.news.data.retrofit

import com.example.news.data.constants.Constants
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private var retrofit: Retrofit? = null
        var api: ApiInterface? = null

        fun restartRetrofit() {
            retrofit = null
        }

        fun getApiClient(): ApiInterface {

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder().apply {
                        addHeader("language", Constants.language)
                    }.build()
                    chain.proceed(request)
                }
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_API_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                api = retrofit!!.create(ApiInterface::class.java)
            }
            return api!!
        }

        private fun bodyToString(request: RequestBody): String? {
            return try {
                val buffer = Buffer()
                request.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }
    }
}