package com.example.islamapplictation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class StateFragmentAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var mItems  : MutableList<Fragment> = arrayListOf()
    private  var mFragmentManager  : FragmentManager
    init {
        this.mFragmentManager = fragmentManager
    }
    fun addFragment(fragment: Fragment){
        this.mItems.add(fragment)
    }
    fun getFragmentPosition(position : Int): Fragment {
        return this.mItems[position]
    }

    override fun getItemCount(): Int {
        return this.mItems.size
    }

    override fun createFragment(position: Int): Fragment {
        return this.mItems[position]
    }
}