package eh.com.currencyexchangeapp.network



import eh.com.workmatetest.model.ClockInResponse
import eh.com.workmatetest.model.ClockOut
import eh.com.workmatetest.model.StaffInfo
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RestApiService {


    @GET("26074/")
     fun getStaffsInfo():Observable<StaffInfo>

    @FormUrlEncoded
    @POST("26074/clock-in/")
    fun postClockIn(@Field("latitude")latitude:Double,
                    @Field("longitude")longitude:Double):Observable<ClockInResponse>


    @FormUrlEncoded
    @POST("26074/clock-out/")
    fun postClockOut(@Field("latitude")latitude:Double,
                    @Field("longitude")longitude:Double):Observable<ClockOut>






}