package eh.com.spweathertest.model
import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    var `data`: Data
)

data class Data(
    @SerializedName("ClimateAverages")
    var climateAverages: List<ClimateAverage>,
    @SerializedName("current_condition")
    var currentCondition: List<CurrentCondition>,
    var request: List<Request>,
    var weather: List<Weather>
)

data class ClimateAverage(
    var month: List<Month>
)

data class Month(
    var absMaxTemp: String,
    @SerializedName("absMaxTemp_F")
    var absMaxTempF: String,
    var avgDailyRainfall: String,
    var avgMinTemp: String,
    @SerializedName("avgMinTemp_F")
    var avgMinTempF: String,
    var index: String,
    var name: String
)

data class CurrentCondition(
    var cloudcover: String,
    @SerializedName("FeelsLikeC")
    var feelsLikeC: String,
    @SerializedName("FeelsLikeF")
    var feelsLikeF: String,
    var humidity: String,
    @SerializedName("observation_time")
    var observationTime: String,
    var precipInches: String,
    var precipMM: String,
    var pressure: String,
    var pressureInches: String,
    @SerializedName("temp_C")
    var tempC: String,
    @SerializedName("temp_F")
    var tempF: String,
    var uvIndex: Int,
    var visibility: String,
    var visibilityMiles: String,
    var weatherCode: String,
    var weatherDesc: List<WeatherDesc>,
    var weatherIconUrl: List<WeatherIconUrl>,
    var winddir16Point: String,
    var winddirDegree: String,
    var windspeedKmph: String,
    var windspeedMiles: String
)

data class WeatherDesc(
    var value: String
)

data class WeatherIconUrl(
    var value: String
)

data class Request(
    var query: String,
    var type: String
)

data class Weather(
    var astronomy: List<Astronomy>,
    var avgtempC: String,
    var avgtempF: String,
    var date: String,
    var hourly: List<Hourly>,
    var maxtempC: String,
    var maxtempF: String,
    var mintempC: String,
    var mintempF: String,
    var sunHour: String,
    @SerializedName("totalSnow_cm")
    var totalSnowCm: String,
    var uvIndex: String
)

data class Astronomy(
    @SerializedName("moon_illumination")
    var moonIllumination: String,
    @SerializedName("moon_phase")
    var moonPhase: String,
    var moonrise: String,
    var moonset: String,
    var sunrise: String,
    var sunset: String
)

data class Hourly(
    var chanceoffog: String,
    var chanceoffrost: String,
    var chanceofhightemp: String,
    var chanceofovercast: String,
    var chanceofrain: String,
    var chanceofremdry: String,
    var chanceofsnow: String,
    var chanceofsunshine: String,
    var chanceofthunder: String,
    var chanceofwindy: String,
    var cloudcover: String,
    @SerializedName("DewPointC")
    var dewPointC: String,
    @SerializedName("DewPointF")
    var dewPointF: String,
    @SerializedName("FeelsLikeC")
    var feelsLikeC: String,
    @SerializedName("FeelsLikeF")
    var feelsLikeF: String,
    @SerializedName("HeatIndexC")
    var heatIndexC: String,
    @SerializedName("HeatIndexF")
    var heatIndexF: String,
    var humidity: String,
    var precipInches: String,
    var precipMM: String,
    var pressure: String,
    var pressureInches: String,
    var tempC: String,
    var tempF: String,
    var time: String,
    var uvIndex: String,
    var visibility: String,
    var visibilityMiles: String,
    var weatherCode: String,
    var weatherDesc: List<WeatherDescX>,
    var weatherIconUrl: List<WeatherIconUrlX>,
    @SerializedName("WindChillC")
    var windChillC: String,
    @SerializedName("WindChillF")
    var windChillF: String,
    @SerializedName("WindGustKmph")
    var windGustKmph: String,
    @SerializedName("WindGustMiles")
    var windGustMiles: String,
    var winddir16Point: String,
    var winddirDegree: String,
    var windspeedKmph: String,
    var windspeedMiles: String
)

data class WeatherDescX(
    var value: String
)

data class WeatherIconUrlX(
    var value: String
)