package eh.com.workmatetest.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import eh.com.currencyexchangeapp.network.CheckingInternetConnection
import eh.com.workmatetest.R
import eh.com.workmatetest.model.StaffInfo
import eh.com.workmatetest.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    var mainViewModel : MainViewModel?=null
    var staffInfo:StaffInfo?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        loadStaffInfo()
    }

    private fun loadStaffInfo()
    {


        if(CheckingInternetConnection.verifyAvailableNetwork(this)) {

           mainViewModel!!.getStaffInfo()!!.observe(this, Observer {

               staffInfo = it
               Log.e("Staff-Info",staffInfo.toString())

           })
        }
        else{

            showdialog("No Internet Connection","Please check your internet connection!")
        }

    }

    fun showdialog(title:String,body:String)
    {
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

}
