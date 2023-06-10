package com.example.islamapplictation.ui.login
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.islamapplictation.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private val binding : ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel:LoginViewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }
    private val fragmentManager : FragmentManager by lazy { supportFragmentManager }
    private val adapter : LoginFragmentsAdapter by lazy { LoginFragmentsAdapter(fragmentManager,lifecycle) }
    private  val TAG = "LoginActivity"
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)

            initFragmentAdapter()


          this.binding.loginTabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.loginViewPager.currentItem= tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        binding.loginViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
            binding.loginTabLayout.selectTab(binding.loginTabLayout.getTabAt(position))

            }
        })
    }
    private fun initFragmentAdapter(){
        binding.loginTabLayout.addTab(binding.loginTabLayout.newTab().setText("Sign In"))
        binding.loginTabLayout.addTab(binding.loginTabLayout.newTab().setText("Sign Up"))
        binding.loginViewPager.adapter =adapter

    }

}
