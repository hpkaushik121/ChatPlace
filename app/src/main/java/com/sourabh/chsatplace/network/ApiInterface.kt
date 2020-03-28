package com.sourabh.chsatplace.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sourabh.chsatplace.BuildConfig
import com.sourabh.chsatplace.pojo.PresenceResponseModel
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiInterface {

    @POST("user/{user}")
    fun getUserPresence(@Path("user") username:String):Deferred<PresenceResponseModel>

    companion object{
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor):ApiInterface{
            val API_KEY="U2thdXNoaWs4NTg1OTQ0NDUyQA=="
            val BASE_URL="http://hostingengine.tech/restapi/openfire/v1/";
            val requestInerceptor=Interceptor(){chain ->
                val builder=  chain.request().newBuilder()

                builder.header("Authorization",API_KEY)

                val request = builder.method(chain
                    .request().method(), chain.request().body())
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient=OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(provideLoggingInterceptor())
                .addInterceptor(requestInerceptor).build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ApiInterface::class.java)
        }

        private fun provideLoggingInterceptor(): HttpLoggingInterceptor
                = HttpLoggingInterceptor().apply {
            level = if(BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }

}