package eh.com.currencyexchangeapp.network
import eh.com.spweathertest.model.SearchResponse
import eh.com.spweathertest.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiService {



    @GET("search.ashx")
    fun getSearch(@Query("key")key:String, @Query("q")q:String?, @Query("format")format:String?):Observable<SearchResponse>

    @GET("weather.ashx")
    fun getWeather(@Query("key")key:String, @Query("q")q:String?, @Query("format")format:String?):Observable<WeatherResponse>





}