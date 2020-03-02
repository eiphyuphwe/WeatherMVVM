package eh.com.workmatetest.reposistories

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonParser
import eh.com.currencyexchangeapp.network.RetrofitInstance
import eh.com.workmatetest.model.ClockInResponse
import eh.com.workmatetest.model.ClockOut
import eh.com.workmatetest.model.ErrorResponse
import eh.com.workmatetest.model.StaffInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class StaffReposistory (application: Application) {

    lateinit var application: Application

    //  private var mCompositeDisposable: CompositeDisposable?=null
    var staffResponse: StaffInfo? = null
    var mutableStaffInfo: MutableLiveData<StaffInfo>? = null
    var mutableClockIn: MutableLiveData<ClockInResponse>? = null
    var mutableClockOut: MutableLiveData<ClockOut>? = null

    init {
        this.application = application
        //  mCompositeDisposable = CompositeDisposable()
        mutableStaffInfo = MutableLiveData()
        mutableClockIn = MutableLiveData()
        mutableClockOut = MutableLiveData()


    }

    fun loadStaffInfo(): MutableLiveData<StaffInfo>? {

        RetrofitInstance.apiService.getStaffsInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onSuccessResponse, this::onError)

//hello
        return this.mutableStaffInfo

    }

    fun clockIn(lat: Double?, lng: Double?):MutableLiveData<ClockInResponse>? {
        RetrofitInstance.apiService.postClockIn(lat, lng)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ onSuccessClockResponse(it) },
                { onClockInError(it) })
           // .subscribe(this::onSuccessClockResponse, this::onClockInError)
        return mutableClockIn

    }

    fun clockOut(lat: Double?, lng: Double?):MutableLiveData<ClockOut>? {
        RetrofitInstance.apiService.postClockOut(lat, lng)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onSuccessClockOutResponse, this::onClockOutError)
        return mutableClockOut

    }


    private fun onSuccessResponse(staffInfo: StaffInfo) {

        this.staffResponse = staffInfo
        this.mutableStaffInfo?.value = this.staffResponse


    }

    private fun onError(error: Throwable) {
        Log.e("Error", error.toString())


    }

    private fun onSuccessClockResponse(clockInResponse: ClockInResponse) {


        this.mutableClockIn?.value = clockInResponse


    }

    private fun onClockInError(error: Throwable) {
        Log.e("Error", error.toString())

        if (error is HttpException) {
            // Kotlin will smart cast at this point
            val errorJsonString = error.response().errorBody()?.string()
            ErrorResponse.error = JsonParser().parse(errorJsonString)
                .asJsonObject["code"]
                .asString




            this.mutableClockIn?.value = null


        }

    }


    private fun onSuccessClockOutResponse(clockOutResponse: ClockOut) {


        this.mutableClockOut?.value = clockOutResponse


    }

    private fun onClockOutError(error: Throwable) {
        Log.e("Error", error.toString())

        if (error is HttpException) {
            // Kotlin will smart cast at this point
            val errorJsonString = error.response().errorBody()?.string()
            ErrorResponse.error = JsonParser().parse(errorJsonString)
                .asJsonObject["code"]
                .asString




            this.mutableClockOut?.value = null


        }

    }
}