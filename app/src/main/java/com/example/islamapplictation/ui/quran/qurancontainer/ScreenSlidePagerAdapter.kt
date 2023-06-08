package com.example.islamapplictation.ui.quran.qurancontainer

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.islamapplictation.ui.quran.quranpage.QuranPageFragment

class ScreenSlidePagerAdapter( fm :FragmentActivity): FragmentStateAdapter(fm) {




        private  val NUM_PAGES = 604
    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return QuranPageFragment(NUM_PAGES-position)
    }
}