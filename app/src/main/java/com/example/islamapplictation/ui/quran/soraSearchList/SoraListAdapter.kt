package com.example.islamapplictation.ui.quran.soraSearchList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islamapplictation.R
import com.example.islamapplictation.databinding.SoraListItemBinding
import com.example.islamapplictation.data.pojo.Sora


class SoraListAdapter () :
    RecyclerView.Adapter<SoraListAdapter.SoraListAdapterViewHolder>() {

    private var soraListArrayListArrayList = ArrayList<Sora>()
    private  val TAG = "SoraListAdapter"
    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(nameArrayList: ArrayList<Sora>) {
        this.soraListArrayListArrayList = nameArrayList
        notifyDataSetChanged()


    }
    private  var soraClickListener: OnSoraClickListener? =null
    fun soraOnCilckListener(soraClickListener: OnSoraClickListener){
        this.soraClickListener=soraClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoraListAdapterViewHolder {
        return SoraListAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.sora_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {

        return soraListArrayListArrayList.size
    }


    override fun onBindViewHolder(holder: SoraListAdapterViewHolder, position: Int) {
        holder.bind(soraListArrayListArrayList[position])
    
    }

    inner class SoraListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: SoraListItemBinding

        init {
            binding = SoraListItemBinding.bind(itemView)

        }

        fun bind(sora: Sora) {
            binding.tvSoraName.text = sora.arabicName
            binding.tvSoraNumber.text = sora.soraNumber.toString()
//            binding.tvSoraStart.text = sora.startPage.toString()
//            binding.tvSoraEnd.text = sora.endPage.toString()
            itemView.setOnClickListener {
               soraClickListener?.onSoraClick(sora.startPage,sora.soraNumber)
            }


        }
    }

    interface  OnSoraClickListener{
       fun onSoraClick(startPage:Int,soraNumber:Int)
    }
}






