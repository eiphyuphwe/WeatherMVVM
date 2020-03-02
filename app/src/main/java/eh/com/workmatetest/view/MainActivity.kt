package eh.com.workmatetest.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import eh.com.currencyexchangeapp.network.CheckingInternetConnection
import eh.com.workmatetest.R
import eh.com.workmatetest.model.ClockInResponse
import eh.com.workmatetest.model.ClockOut
import eh.com.workmatetest.model.ErrorResponse
import eh.com.workmatetest.model.StaffInfo
import eh.com.workmatetest.utils.Utils
import eh.com.workmatetest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(),ProgressDialogFragment.OnCompleteListener {

    var mainViewModel: MainViewModel? = null
    var staffInfo: StaffInfo? = null
    var progressBarValue =0
    var handler = Handler()
    var mPref:SharedPreferences?=null
    var prefsEditor:SharedPreferences.Editor?=null
    var title="Clock In..."


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPref = getPreferences(Context.MODE_PRIVATE)
        prefsEditor = mPref!!.edit()
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        loadStaffInfo()
        onClickEvents()
        //showprogressbar()


    }


    private fun loadStaffInfo() {


        if (CheckingInternetConnection.verifyAvailableNetwork(this)) {

            mainViewModel!!.getStaffInfo()!!.observe(this, Observer {

                staffInfo = it
                if (staffInfo != null) {
                    setdata(staffInfo!!)
                }


            })
        } else {

            showdialog("No Internet Connection", "Please check your internet connection!")
        }

    }


    fun setdata(staffInfo: StaffInfo) {
        txtName.setText(staffInfo.client.name)
        txtCTitle.setText( staffInfo.location.address.country.currency_code+ Utils.convertRoundMode(staffInfo.wage_amount.toDouble()))
        txtClientName.setText(staffInfo.client.name)
        txtAddress.setText(staffInfo.location.address.street_1)
        txtWagesType.setText(staffInfo.wage_type)
        txtManagerName.setText(staffInfo.manager.name)
       //txtContactNumber.setText(staffInfo.manager.phone)
        txtContactNumber.setText(Html.fromHtml("<u>"+staffInfo.manager.phone+"</u>"))
        var clockInstr: String? = mPref?.getString("clockIn","")
        if(!clockInstr.equals(""))
        {
            txtClockIn.setText(clockInstr)
            btnClockIn.setText("Clock Out")
            title = "Clock Out..."
        }
        else{

           // txtClockIn.setText("")
          //  btnClockIn.setText("Clock Out")
        }

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

    fun onClickEvents()
    {

        btnClockIn.setOnClickListener({
            if (CheckingInternetConnection.verifyAvailableNetwork(this)) {



                val fm = supportFragmentManager
                val dialog: ProgressDialogFragment = ProgressDialogFragment.newInstance(
                    title,
                    staffInfo!!.location!!.address!!.latitude,
                    staffInfo!!.location!!.address!!.longitude
                )
                dialog.show(fm, "fragment_dialog")
            }
            else{
                showdialog("No Internet Connection", "Please check your internet connection!")
            }

        })
    }

    override fun onComplete(clkInResponse: ClockInResponse?) {
       txtClockIn.setText(Utils.convertDateTime(clkInResponse!!.clock_in_time))

        prefsEditor!!.putString("clockIn",Utils.convertDateTime(clkInResponse!!.clock_in_time))
        prefsEditor!!.commit()

        title = "Clock Out..."
        btnClockIn.setText("Clock Out")


    }

    override fun onComplete() {

        txtClockIn.setText(ErrorResponse.error)


    }

    override fun onCompleClockOut() {
        txtClockOut.setText(ErrorResponse.error)


    }

    override fun onCompleClockOut(clockOut: ClockOut?) {
       txtClockOut.setText(Utils.convertDateTime(clockOut!!.timesheet.clock_out_time))
        btnClockIn.visibility= View.GONE

        prefsEditor!!.clear()
        prefsEditor!!.commit()

        title = "Clock In..."
        btnClockIn.setText("Clock In")
    }

}
