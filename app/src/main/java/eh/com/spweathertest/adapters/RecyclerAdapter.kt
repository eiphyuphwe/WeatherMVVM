package eh.com.weathertest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eh.com.spweathertest.R
import eh.com.spweathertest.adapters.OnItemClickListerner
import eh.com.spweathertest.database.AppDatabase
import eh.com.spweathertest.database.WeatherDAO
import eh.com.spweathertest.model.Country
import eh.com.spweathertest.reposistories.DatabaseReposistory
import kotlinx.android.synthetic.main.item_layout.view.*


var mcontext:Context?=null
private var dbrepo:DatabaseReposistory?=null
var listerner : OnItemClickListerner?=null
var wDAO:WeatherDAO?=null
class RecyclerAdapter(private val countryList:List<Country>, var context: Context,var itemClick:OnItemClickListerner):RecyclerView.Adapter<RecyclerAdapter.CountryViewHolder> (){
    init {
       mcontext = context
        listerner = itemClick
        wDAO = AppDatabase.getDatabase(mcontext!!).weatherDAO()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.CountryViewHolder {


        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_layout,parent,false)
        return CountryViewHolder(v);

    }

    override fun getItemCount(): Int {

        return countryList.size
       }

    override fun onBindViewHolder(holder: RecyclerAdapter.CountryViewHolder, position: Int) {

        // holder.itemView?.img_country.setImageURI() = countryList[position].name

      /*  holder.itemView.txtCountry?.text = countryList.get(position).areaName.get(0).value + "," +
                countryList.get(position).country.get(0).value + "," +
                countryList.get(position).region.get(0).value*/

        holder.itemView.txtCountry?.text = countryList.get(position).areaName + "," +
                countryList.get(position).region + "," +
                countryList.get(position).country

        holder.itemView.setOnClickListener {

           listerner!!.onItemCLickListener(holder.itemView,countryList.get(position))

          //addCountry(countryList.get(position))
           // getCountry(countryList.get(position))



        }
    }

    class CountryViewHolder(v:View): RecyclerView.ViewHolder(v)

    {
        private var view:View=v


    }



}