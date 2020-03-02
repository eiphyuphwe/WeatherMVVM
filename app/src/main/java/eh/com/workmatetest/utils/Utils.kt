package eh.com.workmatetest.utils

import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun convertDateTime(datestr: String?): String { //  String yourTime = "2019-02-10T19:30:00+00:00";
        val sdf =
            SimpleDateFormat("yyyy-MM-DD'T'hh:mm:ss", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("UTC")
        try {
            calendar.time = sdf.parse(datestr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val output =
            SimpleDateFormat("HH:mm a", Locale.getDefault())
        println(output.format(calendar.time))
        return output.format(calendar.time).toString()
    }

    fun convertRoundMode(currency: Double):String
    {
       // val formatter = DecimalFormat("###,###,##0.00")
       // return formatter.format(amount.toDouble())
        val str: String = NumberFormat.getNumberInstance(Locale.US).format(currency)
        return  str
    }
}