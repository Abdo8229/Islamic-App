package com.example.islamapplictation.ui.azkar.azkarlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.islamapplictation.R
import com.example.islamapplictation.databinding.FragmentAzkarListBinding
import com.example.islamapplictation.ui.quran.qurancontainer.QuranContianerFragmentArgs
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AzkarListFragment : Fragment() {
    private  val TAG = "AzkarListFragment"
    private lateinit var binding : FragmentAzkarListBinding
   private lateinit var args: String
   private val adapter : AzkarListAdapter by lazy {
       AzkarListAdapter(this.requireContext())
   }
    private val viewModel : AzkarListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = AzkarListFragmentArgs.fromBundle(requireArguments()).azkarTypes
//        viewModel = AzkarListViewModel()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAzkarListBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAzharList.addItemDecoration(DividerItemDecoration(this.requireContext(),GridLayoutManager.VERTICAL))
        binding.rvAzharList.adapter =adapter
        adapter.setDataList(viewModel.getAzkar(args))
//            val a = viewModel.getAzkar(this.requireContext(),"دعاءالغضب")
//        Log.d(TAG, "AzkarLis fragment:${a[0]} ")
//        Log.d(TAG, "Azkar args: ${args.toString()}")


    }


}