package eh.com.currencyexchangeapp.network

import okhttp3.Interceptor
import okhttp3.Response

class SupportInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        request = request?.newBuilder()
            ?.addHeader("key", "d3b2c206cdfe43e4bf0140120201203")
            ?.build()
        return chain.proceed(request)
        }
}