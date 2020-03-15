package eh.com.spweathertest.adapters

import android.view.View
import eh.com.spweathertest.model.Country

interface OnItemClickListerner {

    fun onItemCLickListener(view:View,country: Country)

}