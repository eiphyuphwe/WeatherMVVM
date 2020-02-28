package eh.com.currencyexchangeapp.network


import eh.com.workmatetest.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    var BASE_URL = "https://api.helpster.tech/v1/staff-requests/"
    private const val NETWORK_CALL_TIMEOUT = 60
    private var retrofit: Retrofit? = null
   public val apiService: RestApiService
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHttpClient.Builder()
                        .addInterceptor(SupportInterceptor())
                        .addInterceptor(HttpLoggingInterceptor()
                            .apply {
                                level = if (BuildConfig.DEBUG)
                                    HttpLoggingInterceptor.Level.BODY
                                else
                                    HttpLoggingInterceptor.Level.NONE
                            })
                        .readTimeout(NETWORK_CALL_TIMEOUT.toLong(),TimeUnit.SECONDS)
                        .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(),TimeUnit.SECONDS).build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(RestApiService::class.java)
        }
}