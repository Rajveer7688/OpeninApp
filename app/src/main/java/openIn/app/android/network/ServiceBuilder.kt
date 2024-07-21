package openIn.app.android.network

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    private const val URL = "https://api.inopenapp.com"
    private var auth_token: String? = null
    private val okHttp = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(
            Interceptor { chain ->
                var request = chain.request()
                auth_token?.let { token ->
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .addHeader("x-device-type", Build.DEVICE)
                        .addHeader("Accept-Language", Locale.getDefault().language)
                        .build()
                } ?: run {
                    request = request.newBuilder()
                        .addHeader("x-device-type", Build.DEVICE)
                        .addHeader("Accept-Language", Locale.getDefault().language)
                        .build()
                }
                val response = chain.proceed(request)
                response
            }
        )

    private val builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>, token: String?): T {
        auth_token = token
        return retrofit.create(serviceType)
    }
}