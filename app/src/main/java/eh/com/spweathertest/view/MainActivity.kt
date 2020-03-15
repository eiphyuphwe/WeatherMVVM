package eh.com.spweathertest.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import eh.com.currencyexchangeapp.network.CheckingInternetConnection
import eh.com.spweathertest.R
import eh.com.spweathertest.adapters.OnItemClickListerner
import eh.com.spweathertest.database.AppDatabase
import eh.com.spweathertest.database.WeatherDAO
import eh.com.spweathertest.model.Country
import eh.com.spweathertest.model.Result
import eh.com.spweathertest.model.SearchResponse
import eh.com.spweathertest.reposistories.WeatherReposistory
import eh.com.spweathertest.viewmodel.MainViewModel
import eh.com.weathertest.adapters.RecyclerAdapter
import eh.com.weathertest.adapters.mcontext
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,OnItemClickListerner
   {

    var mainViewModel: MainViewModel? = null
    var searchResponse: SearchResponse? = null
       lateinit var adapter: RecyclerAdapter
       var countryList = ArrayList<Result>()
       var cList = ArrayList<Country>()

       var dAO :WeatherDAO?=null




       var weatherReposistory:WeatherReposistory?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherReposistory = WeatherReposistory(application)
        dAO=  AppDatabase.getDatabase(applicationContext).weatherDAO()
        adapter = RecyclerAdapter(cList,this,this)
        rcyList?.adapter = adapter

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        searchView!!.setOnQueryTextListener(this)

        initRcy()


    getAllCountries()

    }



       fun initRcy()
       {

           rcyList?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
       }



       private fun searchCountry(q:String?,format:String?) {


        if (CheckingInternetConnection.verifyAvailableNetwork(this)) {

            mainViewModel!!.search(q,format)!!.observe(this, Observer {

                searchResponse = it
                if (searchResponse != null) {
                    countryList = searchResponse!!.searchApi.result as ArrayList<Result>;
                   cList= convertToCountryData(countryList)
                    adapter = RecyclerAdapter(cList,this,this)
                    rcyList?.adapter = adapter
                }


            })
        } else {

            showdialog("No Internet Connection", "Please check your internet connection!")
        }

    }

       fun convertToCountryData(resultList:ArrayList<Result>):ArrayList<Country>
       {
            var clist = ArrayList<Country>()
           for (c in resultList)
           {
               clist.add(Country(0,c.areaName.get(0).value,
                   c.region.get(0).value,c.country.get(0).value,c.latitude,c.longitude,0))
           }
           return  clist

       }




    fun showdialog(title: String, body: String) {
        val builder = AlertDialog.Builder(this@MainActivity)
        //set title for alert dialog
        builder.setTitle(title)
        //set message for alert dialog
        builder.setMessage(body)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("Yes") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


       override fun onQueryTextChange(newText: String?): Boolean {


           if(newText!!.isEmpty())
           {
               cList.clear()
               adapter = RecyclerAdapter(cList,this,this)
               rcyList?.adapter = adapter
           }
           return false

       }

       override fun onQueryTextSubmit(query: String?): Boolean {

           searchCountry(query,"json")

           return false
       }

      /* override fun onItemCLickListener(country: Country) {
            if(country!= null) {

                findCountry(country)
                //if data has? no ->insert, yes ->non eed insert


                //val intent = Intent(this, DetailActivity::class.java)
               // intent.putExtra("forcast",country )
              //  startActivity(intent)

                *//*dbrepo = DatabaseReposistory(mcontext!!.applicationContext as Application)
                var cty = dbrepo!!.getCountry(
                    countryList.get(position).areaName,
                    countryList.get(position).region
                )
                if(cty==null){

                  val str=  dbrepo!!.addCountry(countryList.get(position))
                    Toast.makeText(mcontext,"str",Toast.LENGTH_LONG).show()
                }
                val intent = Intent(mcontext, DetailActivity::class.java)
                intent.putExtra("forcast", countryList.get(position))
                mcontext!!.startActivity(intent)*//*
            }
       }*/

       override fun onItemCLickListener(view: View, country: Country) {


           getCountry(country)
       }


       fun getAllCountries()
       {


           dAO!!.getAll()!!
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(object : MaybeObserver<List<Country>?> {
                   override fun onSubscribe(d: Disposable) {}

                   override fun onError(e: Throwable) {}
                   override fun onComplete() {



                   }
                   override fun onSuccess(t: List<Country>) {

                       var list = t
                       if(list!=null) {
                           cList = list as ArrayList<Country>
                           setAdapter(cList)

                       }

                   }
               })

       }

       fun setAdapter(cList:List<Country>?)
       {
           cList as ArrayList<Country>
           adapter = RecyclerAdapter(cList, this,this)
           rcyList?.adapter = adapter
       }

       fun addCountry(ctr: Country) {
           Completable.fromAction {
               ctr.time = System.currentTimeMillis()

               dAO!!.insertAll(ctr)
           }.observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
               .subscribe(object : CompletableObserver {
                   override fun onSubscribe(d: Disposable) {}
                   override fun onComplete() {

                       val intent = Intent(mcontext, DetailActivity::class.java)
                       intent.putExtra("forcast",ctr)
                       mcontext!!.startActivity(intent)
                   }

                   override fun onError(e: Throwable) {

                   }
               })

       }

       fun deleteCountry(delcty: Country,addCty:Country) {
           Completable.fromAction {


               dAO!!.delete(delcty)
           }.observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
               .subscribe(object : CompletableObserver {
                   override fun onSubscribe(d: Disposable) {}
                   override fun onComplete() {

                       addCountry(addCty)
                   }

                   override fun onError(e: Throwable) {

                   }
               })

       }


       fun getCountry(country: Country) {

           dAO!!.getCountry(country.areaName, country.region)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(object : MaybeObserver<Country?> {
                   override fun onSubscribe(d: Disposable) {}

                   override fun onError(e: Throwable) {}
                   override fun onComplete() {

                       getCountries(country)

                   }
                   override fun onSuccess(t: Country) {



                           val intent = Intent(mcontext, DetailActivity::class.java)
                           intent.putExtra("forcast",country)
                           mcontext!!.startActivity(intent)

                   }
               })


       }


       fun getCountries(country: Country) {

           dAO!!.getAllAsc()!!
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(object : MaybeObserver<List<Country>?> {
                   override fun onSubscribe(d: Disposable) {}

                   override fun onError(e: Throwable) {}
                   override fun onComplete() {


                   }
                   override fun onSuccess(t: List<Country>) {



                       if(t.size>=10){


                           deleteCountry(t.get(0),country)

                       }
                       else {
                           addCountry(country)
                       }

                   }
               })


       }




   }
