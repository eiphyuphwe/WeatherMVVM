package eh.com.currencyexchangeapp.network

import okhttp3.Interceptor
import okhttp3.Response

class SupportInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        request = request?.newBuilder()
            ?.addHeader("Authorization", "token e945ae028e2355e123cfdf1b4fbb81ad4e5b2ebc")
            ?.build()
        return chain.proceed(request)
        }
}