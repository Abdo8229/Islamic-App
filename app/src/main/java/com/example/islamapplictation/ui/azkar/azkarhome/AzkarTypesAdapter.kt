package com.example.islamapplictation.ui.azkar.azkarhome

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.islamapplictation.R
import com.example.islamapplictation.data.pojo.ZekrTypes
import com.example.islamapplictation.databinding.ListItemAzkarTypesBinding
import java.text.NumberFormat
import java.util.Locale


class AzkarTypesAdapter :
    RecyclerView.Adapter<AzkarTypesAdapter.AzkarTypesAdapterViewHolder>() {

    private lateinit var azkarTypesHashSet: HashSet<ZekrTypes>
    private  var azkarClickListener: AzkarClickListener? =null
    fun setAzkarListener(azkarClickListener: AzkarClickListener){
        this.azkarClickListener=azkarClickListener
    }
    fun setDataList(zekrTypesHashSet: HashSet<ZekrTypes>) {
        this.azkarTypesHashSet = zekrTypesHashSet
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarTypesAdapterViewHolder {

        return AzkarTypesAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_azkar_types, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return azkarTypesHashSet.size
    }

    override fun onBindViewHolder(holder: AzkarTypesAdapterViewHolder, position: Int) {
        holder.bind(azkarTypesHashSet.elementAt(position),position,azkarClickListener)
    }

    inner class AzkarTypesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var binding: ListItemAzkarTypesBinding

        init {
            binding = ListItemAzkarTypesBinding.bind(itemView)
        }

        fun bind(zekrTypes: ZekrTypes, position: Int, azkarClickListener: AzkarClickListener?) {
            binding.tvZekerName.text = zekrTypes.zekrName
            val numberFormat : NumberFormat = NumberFormat.getNumberInstance(Locale("ar","EG"))
            binding.tvZekrNumber.text = "< ${ numberFormat.format(position + 1) } >"
            itemView.setOnClickListener {
                azkarClickListener?.onZekrClick(zekrTypes.zekrName)
            }
        }


    }

}

interface AzkarClickListener {
    fun onZekrClick(name: String);
}

