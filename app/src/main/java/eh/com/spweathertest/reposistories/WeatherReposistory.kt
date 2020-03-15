package eh.com.spweathertest.reposistories

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import eh.com.currencyexchangeapp.network.RetrofitInstance
import eh.com.spweathertest.model.SearchResponse
import eh.com.spweathertest.model.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherReposistory (application: Application) {

    lateinit var application: Application
    val key="d3b2c206cdfe43e4bf0140120201203"

    var searchResponse: SearchResponse? = null
    var mutableSearchResponse: MutableLiveData<SearchResponse>? = null

    var weatherResponse:WeatherResponse?=null
    var mutableWeatherResponse:MutableLiveData<WeatherResponse>?=null


    init {
        this.application = application
        //  mCompositeDisposable = CompositeDisposable()
        mutableSearchResponse = MutableLiveData()
        mutableWeatherResponse = MutableLiveData()



    }

    fun loadSearchCity(q:String?,format:String?): MutableLiveData<SearchResponse>? {

        RetrofitInstance.apiService.getSearch(key,q,format)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onSuccessResponse, this::onError)

//hello
        return this.mutableSearchResponse

    }



    private fun onSuccessResponse(searchResponse: SearchResponse) {

        this.searchResponse = searchResponse
        this.mutableSearchResponse?.value = this.searchResponse


    }

    private fun onError(error: Throwable) {
        Log.e("Error", error.toString())


    }


    fun loadDetailLocalWeather(q:String?,format:String?): MutableLiveData<WeatherResponse>? {

        RetrofitInstance.apiService.getWeather(key,q,format)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onSuccessWeatherResponse, this::onWeatherError)

//hello
        return this.mutableWeatherResponse

    }

    private fun onSuccessWeatherResponse(weatherResponse: WeatherResponse) {

        this.weatherResponse = weatherResponse
        this.mutableWeatherResponse?.value = this.weatherResponse


    }

    private fun onWeatherError(error: Throwable) {
        Log.e("Error", error.toString())


    }






}