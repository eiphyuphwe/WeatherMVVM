package eh.com.spweathertest.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(
    @SerializedName("search_api")
    var searchApi: SearchApi
):Parcelable{}

@Parcelize
data class SearchApi(
    var result: List<Result>
):Parcelable{}

@Parcelize
data class Result(
    var areaName: List<AreaName>,
    var country: List<Country1>,
    var latitude: String,
    var longitude: String,
    var population: String,
    var region: List<Region>,
    var weatherUrl: List<WeatherUrl>
):Parcelable{}

@Parcelize
data class AreaName(
    var value: String
):Parcelable{}

@Parcelize
data class Country1(
    var value: String
):Parcelable{}

@Parcelize
data class Region(
    var value: String
):Parcelable{}

@Parcelize
data class WeatherUrl(
    var value: String
):Parcelable{}