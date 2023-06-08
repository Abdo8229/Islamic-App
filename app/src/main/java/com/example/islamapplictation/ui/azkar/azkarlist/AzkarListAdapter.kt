package com.example.islamapplictation.ui.azkar.azkarlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islamapplictation.R
import com.example.islamapplictation.data.pojo.Zekr
import com.example.islamapplictation.databinding.ListItemAzkarBinding

class AzkarListAdapter(private val context: Context) :
    RecyclerView.Adapter<AzkarListAdapter.AzkarListViewHolder>() {
    private lateinit var zekrArrayList: ArrayList<Zekr>

    fun setDataList(zekrArrayList: ArrayList<Zekr>) {
        this.zekrArrayList = zekrArrayList
        notifyDataSetChanged()
    }

    private var zekrrClickListener: ZekrClickListener? = null
    fun setAzkarListener(zekarClickListener: ZekrClickListener) {
        this.zekrrClickListener = zekarClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarListViewHolder {
        return AzkarListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_azkar, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return zekrArrayList.size
    }

    override fun onBindViewHolder(holder: AzkarListViewHolder, position: Int) {
        holder.bind(zekrArrayList.get(position), position)
    }

    inner class AzkarListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var binding: ListItemAzkarBinding

        init {
            binding = ListItemAzkarBinding.bind(itemView)
        }

        fun bind(zekr: Zekr, position: Int) {
            binding.tvZekerNameListItemAzkar.text = zekr.zekr
            var zekrCount: Int = if (zekr.count.isEmpty()) {
                1
            } else {
                zekr.count.toInt()
            }
            binding.tvZekrCount.text = zekrCount.toString()
            itemView.setOnClickListener {
                if (zekrCount == 1) {
                    zekrArrayList.removeAt(position)
                    notifyDataSetChanged()
                } else {
                    zekrCount -= 1
                    binding.tvZekrCount.text = zekrCount.toString()

                }
            }
        }


    }

}

interface ZekrClickListener {
    fun onZekrClick(count: Int);
}
