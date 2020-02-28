package eh.com.workmatetest.reposistories

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import eh.com.currencyexchangeapp.network.RetrofitInstance
import eh.com.workmatetest.model.StaffInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StaffReposistory (application: Application){

    lateinit var application:Application

  //  private var mCompositeDisposable: CompositeDisposable?=null
    var staffResponse : StaffInfo?= null
    var mutableStaffInfo : MutableLiveData<StaffInfo>?=null

    init {
        this.application = application
      //  mCompositeDisposable = CompositeDisposable()
        mutableStaffInfo = MutableLiveData()


    }

    fun loadStaffInfo(): MutableLiveData<StaffInfo>?
    {

            RetrofitInstance.apiService.getStaffsInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onSuccessResponse,this::onError)

//hello
        return  this.mutableStaffInfo

    }

    private fun onSuccessResponse(staffInfo: StaffInfo)
    {

        this.staffResponse = staffInfo
        this.mutableStaffInfo?.value = this.staffResponse


    }

    private fun onError(error:Throwable)
    {
        Log.e("Error",error.toString())

    }
}