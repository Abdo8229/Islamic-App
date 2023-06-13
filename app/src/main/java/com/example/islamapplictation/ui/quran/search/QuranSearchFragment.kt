package com.example.islamapplictation.ui.quran.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.islamapplictation.data.pojo.Aya
import com.example.islamapplictation.databinding.FragmentQuranSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class QuranSearchFragment : Fragment() {

    private lateinit var binding: FragmentQuranSearchBinding
    private val viewModle: QuranSearchViewModel by viewModels()
    private var ayaArrayList: List<Aya> = listOf()
    val adapter: QuranSearchAdapter by lazy {
        QuranSearchAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModle = QuranSearchViewModel(this.requireContext())
        binding = FragmentQuranSearchBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearchFragment.addItemDecoration(
            DividerItemDecoration(
                this.requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvSearchFragment.adapter = adapter
        binding.edSearchQuran.clearFocus()
        binding.edSearchQuran.setOnQueryTextListener(object : OnQueryTextListener {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    ayaArrayList = viewModle.getSearchResults(newText.toString())
                }
                adapter.setDataList(ayaArrayList)
                return true
            }
        })
        adapter.ayaOnCilckListener(object : AyaOnClickListener {
            override fun onClickListener(ayaPage: Int, soraNumber: Int) {
                findNavController().navigate(
                    QuranSearchFragmentDirections.actionQuranSearchFragmentToQuranFragment(
                        ayaPage,
                        soraNumber
                    )
                )

            }
        })
    }


}