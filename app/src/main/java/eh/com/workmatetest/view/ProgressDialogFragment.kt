package eh.com.workmatetest.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import eh.com.currencyexchangeapp.network.CheckingInternetConnection
import eh.com.workmatetest.R
import eh.com.workmatetest.model.ClockInResponse
import eh.com.workmatetest.model.ClockOut
import eh.com.workmatetest.viewmodel.MainViewModel



class ProgressDialogFragment : DialogFragment() {
    private var txtCTitle: TextView? = null
    private var txtCancel: TextView? = null
    private var progressBar: ProgressBar? = null
    var mainViewModel: MainViewModel? = null
    var title:String ?=null
    var lat:Double?=null
    var lng:Double?=null
    var clockInResponse:ClockInResponse?=null
    var clockOutResponse:ClockOut?=null
    var ctx:Context ?=null
    var isCancelled = false






    interface OnCompleteListener {
        fun onComplete(clkInResponse: ClockInResponse?)
        fun onComplete()
        fun onCompleClockOut(clockOut: ClockOut?)
        fun onCompleClockOut()
    }

    private var mListener: OnCompleteListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            mListener = activity as OnCompleteListener

        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnCompleteListener")
        }
    }

    override fun onResume() {
        super.onResume()
        ctx = requireContext()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        title = getArguments()!!.getString("title");
        lat = getArguments()!!.getDouble("lat")
        lng = getArguments()!!.getDouble("lng");
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.progress_dialog_fragment, container)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        txtCTitle = view.findViewById<View>(R.id.txtCTitle) as TextView
        txtCancel = view.findViewById<View>(R.id.txtCancel) as TextView
        progressBar = view.findViewById<View>(R.id.ProgressBar) as ProgressBar
        txtCTitle!!.setText(title)
        showprogressbar()

        txtCancel!!.setText(Html.fromHtml(getString(R.string.cancel)));
        txtCancel!!.setOnClickListener({

            var dialog = getDialog()

            isCancelled = true
            dialog.dismiss()

        })
    }

    override fun onStart() {
        super.onStart()
        var dialog = getDialog()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setLayout(width, height)
        }
    }

    companion object {
        fun newInstance(title: String?,lat: Double,lng: Double): ProgressDialogFragment {
            val frag = ProgressDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putDouble("lat",lat)
            args.putDouble("lng",lng)
            frag.arguments = args
            return frag
        }
    }

    fun showprogressbar()
    {
        var i = 0

      val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) { //this will be done every 1000 milliseconds ( 1 seconds )

                if(isCancelled){
                    cancel()
                }
                else {
                    i++
                    progressBar!!.setProgress(i * 100 / (10000 / 1000))
                }
            }

            override fun onFinish() { //the progressBar will be invisible after 10 000 miliseconds ( 10 sec)
                i++;
                progressBar!!.setProgress(100)

                if (CheckingInternetConnection.verifyAvailableNetwork(ctx)) {
                    if (title!!.equals("Clock In...")) {
                        clockIn(lat, lng)
                    } else {
                        clockOut(lat, lng)
                    }
                }
                else{
                    showdialog("No Internet Connection", "Please check your internet connection!")
                }
            }


        }
        timer.start()
    }

    private fun clockIn(lat:Double?,lng:Double?) {


            mainViewModel!!.clockIn(lat,lng)!!.observe(this, Observer {

                clockInResponse = it

                if(clockInResponse!=null) {
                    this.mListener?.onComplete(clockInResponse)
                }
                else{


                    this.mListener?.onComplete()
                }
                var dialog = getDialog()
                dialog.dismiss()

            })


    }

    private fun clockOut(lat:Double?,lng:Double?) {


        mainViewModel!!.clockOut(lat,lng)!!.observe(this, Observer {

            clockOutResponse = it

            if(clockOutResponse!=null) {
                this.mListener?.onCompleClockOut(clockOutResponse)
            }
            else{


                this.mListener?.onCompleClockOut()
            }
            var dialog = getDialog()
            dialog.dismiss()

        })


    }

    fun showdialog(title: String, body: String) {
        val builder = this!!.activity?.let { AlertDialog.Builder(it) }
        //set title for alert dialog
        builder!!.setTitle(title)
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

    override fun onPause() {
        super.onPause()
        isCancelled=true
    }

}