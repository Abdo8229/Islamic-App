package com.example.islamapplictation.ui.azkar.azkarhome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.islamapplictation.databinding.FragmentAzkarHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AzkarHomeFragment : Fragment() {
    private val viewModel: AzkarTypesViewModel by viewModels()
    private val adapter: AzkarTypesAdapter by lazy {
        AzkarTypesAdapter()

    }
    private lateinit var binding: FragmentAzkarHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAzkarHomeBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
//        inflater.inflate(R.layout.fragment_azkar_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAzkarHome.addItemDecoration(
            DividerItemDecoration(
                this.requireContext(),
                GridLayoutManager.VERTICAL
            )
        )
//        viewModel = AzkarTypesViewModel()
        binding.rvAzkarHome.adapter = adapter
        adapter.setDataList(viewModel.getAzkarType())
        adapter.setAzkarListener(object :AzkarClickListener{
            override fun onZekrClick(name: String) {
                findNavController().navigate(
                    AzkarHomeFragmentDirections.actionAzkarHomeFragmentToAzkarListFragment(name)
                )
            }
        })
    }
}