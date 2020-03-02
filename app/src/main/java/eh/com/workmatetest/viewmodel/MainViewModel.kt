package eh.com.workmatetest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import eh.com.workmatetest.model.ClockInResponse
import eh.com.workmatetest.model.ClockOut
import eh.com.workmatetest.model.StaffInfo
import eh.com.workmatetest.reposistories.StaffReposistory

class MainViewModel (val app:Application) : AndroidViewModel(app)
{

    var reposistory:StaffReposistory?=null



    init {

        reposistory = StaffReposistory(app)


    }

    fun getStaffInfo():MutableLiveData<StaffInfo>?
    {
        return reposistory?.loadStaffInfo()
    }


    fun clockIn(lat:Double?,lng:Double?):MutableLiveData<ClockInResponse>?
    {
        return reposistory?.clockIn(lat,lng)
    }

    fun clockOut(lat:Double?,lng:Double?):MutableLiveData<ClockOut>?
    {
        return reposistory?.clockOut(lat,lng)
    }



}