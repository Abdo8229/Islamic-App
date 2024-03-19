package com.example.islamapplictation.ui.quran.soraSearchList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.islamapplictation.databinding.FragmentSoraListBinding
import com.example.islamapplictation.data.pojo.Sora
import kotlinx.coroutines.launch

class SoraListFragment : Fragment() {
    private val viewModel: SoraListViewModel by activityViewModels()
    private var soraArrayList: ArrayList<Sora> = ArrayList()
    private lateinit var binding: FragmentSoraListBinding
//    private val TAG = "SoraListFragment"
    private val adapter: SoraListAdapter by lazy {
        SoraListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        viewModel = ViewModelProvider(this)[SoraListViewModel::class.java]
        viewModel.getDataByEvent(SoraListEvent.GetAllSoraEvent)
        binding = FragmentSoraListBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.clearFocus()
//        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
//                Toast.makeText(requireContext(), "$newText", Toast.LENGTH_SHORT).show()
//                return true
//            }
//        })

        binding.searchView.setOnClickListener {
            NavHostFragment
                .findNavController(this@SoraListFragment)
                .navigate(
                    SoraListFragmentDirections
                        .actionSoraListFragmentToQuranSearchFragment()
                )
        }


//        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer {
//            adapter.setDataList(it)
//            for(i in it) {
//                Log.d(TAG, "onViewCreated: ${it}")
//            }
//        })


//        binding.edSearchQuranSoraList.setOnClickListener(object : OnClickListener {
//            override fun onClick(v: View?) {
//                NavHostFragment
//                    .findNavController(this@SoraListFragment)
//                    .navigate(
//                        SoraListFragmentDirections
//                            .actionSoraListFragmentToQuranSearchFragment()
//                    )
//            }
//        })
        binding.rvSoraList.addItemDecoration(
            DividerItemDecoration(
                this.requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvSoraList.adapter = adapter
        recursiveLifecycle()

        adapter.soraOnClickListener(object : SoraListAdapter.OnSoraClickListener {
            override fun onSoraClick(startPage: Int, soraNumber: Int) {
                findNavController().navigate(
                    SoraListFragmentDirections.actionSoraListFragmentToQuranFragment(
                        startPage,
                        soraNumber
                    )
                )

            }
        })
    }

    private fun recursiveLifecycle() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.soraListStatusStateFlow.collect { event ->
                when (event) {
                    is SoraListStatus.Success -> {
                        soraArrayList = event.resultArrayList
                        adapter.setDataList(soraArrayList)
                        binding.progressBar.isVisible = false

                    }

                    is SoraListStatus.Failure -> {
                        val error: ArrayList<Sora> =
                            arrayListOf(Sora(0, 0, 0, event.errorText, "Error"))
                        adapter.setDataList(error)
                        binding.progressBar.isVisible = true
                    }

                    is SoraListStatus.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    else -> Unit
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.searchView.clearFocus()
//        viewLifecycleOwner.lifecycleScope.cancel()
    }

}