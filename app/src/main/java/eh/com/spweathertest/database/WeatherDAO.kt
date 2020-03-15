package eh.com.spweathertest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import eh.com.spweathertest.model.Country
import io.reactivex.Maybe


@Dao
interface WeatherDAO{

    @Query("SELECT * FROM country WHERE areaName LIKE :areaName AND region LIKE :region")
    fun getCountry(areaName:String?,region:String?):Maybe<Country>

    @Query("SELECT * FROM country ORDER BY time DESC")
    fun getAll(): Maybe<List<Country>>?

    @Query("SELECT * FROM country ORDER BY time ASC")
    fun getAllAsc(): Maybe<List<Country>>?
    @Insert
    fun insertAll(country: Country)

    @Delete
    fun delete(country : Country)





}