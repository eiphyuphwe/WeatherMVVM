package eh.com.spweathertest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import eh.com.spweathertest.model.Country
import eh.com.spweathertest.model.SearchResponse
import eh.com.spweathertest.model.WeatherResponse
import eh.com.spweathertest.reposistories.DatabaseReposistory
import eh.com.spweathertest.reposistories.WeatherReposistory

class MainViewModel (val app:Application) : AndroidViewModel(app)
{

    var reposistory: WeatherReposistory?=null
    var dbRespo: DatabaseReposistory?=null




    init {

        reposistory = WeatherReposistory(app)
       // dbRespo = DatabaseReposistory(app)


    }

    fun search(q:String?,format:String?):MutableLiveData<SearchResponse>?
    {
        return reposistory?.loadSearchCity(q,format)
    }

    fun loadDetailLocalWeather(q:String?,format:String?):MutableLiveData<WeatherResponse>?
    {
        return reposistory?.loadDetailLocalWeather(q,format)
    }

    fun loadCountryAll(): MutableLiveData<List<Country>>?
    {
        return dbRespo!!.getAll()
    }

    fun loadCountry(area:String?,region:String?):Country?
    {
        return dbRespo!!.getCountry(area,region)
    }

    fun saveCountry(country: Country)
    {
        dbRespo!!.addCountry(country)
    }



}