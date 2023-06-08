package com.example.islamapplictation.ui.quran.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.islamapplictation.R
import com.example.islamapplictation.data.pojo.Aya
import com.example.islamapplictation.databinding.ListItemQuranSearchBinding
class QuranSearchAdapter :

    RecyclerView.Adapter<QuranSearchAdapter.QuranSearchAdapterViewHolder>() {
    private var ayaArrayList: List<Aya>  = listOf()
//    private  val TAG = "QuranSearchAdapter"
@SuppressLint("NotifyDataSetChanged")
fun setDataList(ayaArrayList: List<Aya>) {
        this.ayaArrayList = ayaArrayList
        notifyDataSetChanged()
    }
    private  var ayaClickListener:AyaOnClickListener? =null
    fun ayaOnCilckListener(ayaClickListener:AyaOnClickListener){
        this.ayaClickListener=ayaClickListener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuranSearchAdapterViewHolder {
        return QuranSearchAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_quran_search, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return ayaArrayList.size
    }

    override fun onBindViewHolder(holder: QuranSearchAdapterViewHolder, position: Int) {
        holder.bind(ayaArrayList[position])
    }

    inner class QuranSearchAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemQuranSearchBinding

        init {
            binding = ListItemQuranSearchBinding.bind(itemView)

        }

        fun bind(aya: Aya) {
            binding.tvSearchItemAyaNumber.text =  aya.aya_no.toString()
            binding.tvSearchItemSoraNumber.text = aya.sora.toString()
            binding.tvSearchItemSoraName.text = aya.sora_name_ar
            binding.tvSearchItemAyaContent.text = aya.aya_text_emlaey
                    itemView.setOnClickListener {
                        ayaClickListener?.onClickListener(aya.page,aya.sora)
            }
        }
    }

}
interface AyaOnClickListener{
    fun onClickListener(ayaPage:Int,soraNumber:Int)
}