package eh.com.spweathertest.reposistories

import androidx.lifecycle.MutableLiveData
import eh.com.spweathertest.database.WeatherDAO
import eh.com.spweathertest.model.Country
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class DatabaseReposistory(dao: WeatherDAO) {

    var mutableCountry: MutableLiveData<Country>? = null
    var mutableCountryList: MutableLiveData<List<Country>>?=null
    var wDAO:WeatherDAO?=null
    init {
        this.wDAO = dao
        mutableCountry = MutableLiveData()
        mutableCountryList = MutableLiveData()
    }

   /* fun getCountry(areaName:String?,region:String?):Country
    {
        return  this.wDAO!!.getCountry(areaName,region)
    }*/



    var ctr: Country? = null
    fun getCountry(area: String?, region: String?): Country? {
        this.wDAO!!.getCountry(area, region)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<Country?> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                  //  mutableCountry!!.value = null
                    ctr = null
                }
                override fun onSuccess(t: Country) {
                   ctr = t
                }
            })

        return ctr
    }
    fun saveCountry(country: Country)
    {
        country.time = System.currentTimeMillis()
        this.wDAO!!.insertAll(country)
    }

    var str = ""
    fun addCountry(ctr: Country): String? {
        Completable.fromAction {
            ctr.time = System.currentTimeMillis()
            this.wDAO!!.insertAll(ctr)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    str = "Success Added"
                }

                override fun onError(e: Throwable) {
                    str = "error"
                }
            })
        return str
    }



    fun getAll():MutableLiveData<List<Country>>? {
        wDAO!!.getAll()!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<List<Country>?> { clist ->mutableCountryList!!.value = clist })
        return mutableCountryList
    }



}


