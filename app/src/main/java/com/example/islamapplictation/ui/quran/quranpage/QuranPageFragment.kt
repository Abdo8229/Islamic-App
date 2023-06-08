package com.example.islamapplictation.ui.quran.quranpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.islamapplictation.databinding.FragmentPageQuranBinding

class QuranPageFragment( pageNumber: Int) : Fragment() {
    lateinit var binding: FragmentPageQuranBinding
    private  val viewModel: QuranViewModel by lazy {ViewModelProvider(this)[QuranViewModel::class.java]  }
    private var  _pageNumber :Int
    init {
        this._pageNumber = pageNumber
    }
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPageQuranBinding.inflate(layoutInflater, container, false)
//return inflater.inflate(R.layout.fragment_quran,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val page1: Int = viewModel.getQuranImagesPageByNumber(requireContext(), _pageNumber)
        binding.imvQuranPage.setImageResource(page1)


    }
}