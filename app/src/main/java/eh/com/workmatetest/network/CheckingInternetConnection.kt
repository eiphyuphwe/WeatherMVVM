package eh.com.currencyexchangeapp.network

import android.content.Context
import android.net.ConnectivityManager

object CheckingInternetConnection {




    fun verifyAvailableNetwork(context: Context?):Boolean{
        val connectivityManager= context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }


}