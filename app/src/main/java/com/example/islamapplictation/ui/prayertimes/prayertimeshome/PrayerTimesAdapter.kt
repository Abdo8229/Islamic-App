package com.example.islamapplictation.ui.prayertimes.prayertimeshome

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islamapplictation.R
import com.example.islamapplictation.data.pojo.prayertimes.PrayerTiming
import com.example.islamapplictation.data.pojo.prayertimes.Timings
import com.example.islamapplictation.databinding.ListItemPrayerHomeBinding


class PrayerTimesAdapter(private val context: Context) :
    RecyclerView.Adapter<PrayerTimesAdapter.PrayerTimesAdapterViewHolder>() {
    private var prayerTimesArrayList = ArrayList<PrayerTiming>()
    private  val TAG = "PrayerTimesAdapter"
    fun setDataList(nameArrayList: ArrayList<PrayerTiming>) {
        this.prayerTimesArrayList = nameArrayList
//        Log.d(TAG, "setDataList: ${nameArrayList[0]}")
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PrayerTimesAdapterViewHolder {
        return PrayerTimesAdapterViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_prayer_home, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return prayerTimesArrayList.size
    }

    override fun onBindViewHolder(holder: PrayerTimesAdapterViewHolder, position: Int) {
        holder.bind(prayerTimesArrayList[position])
    }

    inner class PrayerTimesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      private var binding: ListItemPrayerHomeBinding

        init {
            binding = ListItemPrayerHomeBinding.bind(itemView)
        }

        fun bind(timings: PrayerTiming) {
            binding.tvPrayerName.text = timings.prayerName
            binding.tvPrayerTime.text = timings.prayerTime
        }

    }
}