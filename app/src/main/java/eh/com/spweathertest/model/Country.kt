package eh.com.spweathertest.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Parcelize
@Entity(tableName = "country")
data class Country(
    @PrimaryKey(autoGenerate = true) val uid:Int,
    var areaName :String?,
    var region:String?,
    var country:String?,
    var latitude:String?,
    var longitude:String?,
    var time:Long?
) : Parcelable {


}