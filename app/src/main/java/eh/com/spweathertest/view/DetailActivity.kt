package eh.com.spweathertest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import eh.com.currencyexchangeapp.network.CheckingInternetConnection
import eh.com.spweathertest.R
import eh.com.spweathertest.model.Country
import eh.com.spweathertest.model.WeatherResponse
import eh.com.spweathertest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.detail_layout.*



class DetailActivity : AppCompatActivity() {

    var mainViewModel: MainViewModel? = null
    var detailResponse: WeatherResponse? = null
    var country:Country?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_layout)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        init()
    }

    fun init()
    {
        country = intent.getParcelableExtra("forcast")
        if(country!=null)
        {
            if (CheckingInternetConnection.verifyAvailableNetwork(this)) {

                var q=country!!.latitude+","+country!!.longitude
                mainViewModel!!.loadDetailLocalWeather(q,"json")!!.observe(this, Observer {


                    detailResponse = it
                    if(detailResponse!=null)
                    {
                        val aUrl: String = detailResponse!!.data.currentCondition
                            .get(0).weatherIconUrl.get(0).value.replace("http", "https")

                        Picasso.get().load(
                           aUrl).into(img_weather)

                        txt_tempc.setText(detailResponse!!.data.currentCondition.get(0).tempC+"\u2103")
                        txt_tempF.setText(detailResponse!!.data.currentCondition.get(0).tempF+ "\u2109")
                        txtDesp.setText(detailResponse!!.data.currentCondition.get(0).weatherDesc.get(0).value)
                    }
                })
            }
            else{

                //showdialog("No Internet Connection", "Please check your internet connection!")
            }
            }
        }





}