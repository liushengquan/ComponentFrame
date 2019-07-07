package com.example.liushengquan.douban.api

import android.content.Context
import com.example.impl.util.NetUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by liushengquan on 2018/1/6.
 */
class ApiRetrofit private constructor(context: Context) {

    private val DOUBAN_BASE_URL = "https://api.douban.com/"
    private var mContext = context
    private var mRetrofit: Retrofit


    private var interceptor = Interceptor { chain ->
        val cacheBuilder = CacheControl.Builder()
        cacheBuilder.maxAge(0, TimeUnit.SECONDS)
        cacheBuilder.maxStale(365, TimeUnit.DAYS)
        val cacheControl = cacheBuilder.build()

        var request = chain!!.request()
        if (!NetUtils.checkNetWorkIsAvailable(mContext)) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()

        }
        val originalResponse = chain.proceed(request)
        if (NetUtils.checkNetWorkIsAvailable(mContext)) {
            val maxAge = 0 // read from cache
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=$maxAge")
                    .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }
    }

    init {
        val httpCacheDirectory = File(mContext.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .build()

        mRetrofit = Retrofit.Builder()
                .baseUrl(DOUBAN_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        private lateinit var mInstance: ApiRetrofit

        fun getInstance(context: Context): ApiRetrofit {
            if (mInstance == null) {
                mInstance = ApiRetrofit(context)
            }
            return mInstance
        }
    }

    fun getApiService(service: Any): Any? {
        return mRetrofit.create(service::class.java)
    }

}