package com.example.islamapplictation.ui.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.islamapplictation.ui.login.signin.SignInFragment
import com.example.islamapplictation.ui.login.signup.SignUpFragment

class LoginFragmentsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position==1){
            return SignUpFragment()
        }
        return SignInFragment()
    }
}