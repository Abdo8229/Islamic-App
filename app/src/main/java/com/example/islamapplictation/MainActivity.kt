package com.example.islamapplictation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.islamapplictation.data.pojo.user.User
import com.example.islamapplictation.databinding.ActivityMainBinding
import com.example.islamapplictation.ui.profile.ProfileActivity
import com.example.islamapplictation.util.AzanPrayeres
import com.example.islamapplictation.util.CheckPermisions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val checkPermissions: CheckPermisions by lazy { CheckPermisions(this) }
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val mDatabase: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }
    private var users: ArrayList<User> = arrayListOf()
    private lateinit var user: User
    private val A = "MainActivity"

    //    private val mFireStorage:
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        val quranDataBase = QuranDatabase.getInstance(this@MainActivity).quranDao()
//        GlobalScope.launch (Dispatchers.IO){
//            Log.d(TAG, "onCreate: ${quranDataBase.getSoraByNumber(1)}")
//        }
        checkPermissions.checkPermission()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.soraListFragment,
                R.id.azkarHomeFragment,
                R.id.quranSearchFragment,
                R.id.quranFragment,
                R.id.azkarListFragment,
                R.id.prayerTimesFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        AzanPrayeres.registerPrayers(this)

        binding.appBarMain.toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val headerView: View = navView.getHeaderView(0)
        val llProfile: LinearLayout = headerView.findViewById(R.id.nav_header)
        llProfile.setOnClickListener {
            goToProfileActivity()
        }
//        val profileImage = headerView.findViewById(R.id.img_user_profile) as CircleImageView
        val userName = headerView.findViewById(R.id.tv_user_name) as TextView

        Log.d(A, "onCreate: ${mAuth.currentUser!!.uid}")

        once()
        Log.d(A, "onCreate:${users.size} ")
//        Log.d(A, "onCreate:${users[0]} ")

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


    }

private fun once(){
//    mDatabase.database.getReference("Users").child(mAuth.currentUser!!.uid).get().addOnSuccessListener {
//        user = it.getValue(User::class.java)!!
//        Log.d(A, "onCreate: ${user.fullName} ")
//    }
    mDatabase.child("Users").addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for(data in snapshot.children) {
                users.add(data.getValue(User::class.java)!!)
                Log.d(A, "onDataChange: ${data.getValue(User::class.java)!!.fullName} ")
            }

        }

        override fun onCancelled(error: DatabaseError) {
            Log.d(A, "onCancelled: ${error.message}")
        }
    })

}
//   private fun setTextColorForMenuItem(menuItem: MenuItem, @ColorRes color: Int) {
//        val spanString = SpannableString(menuItem.title.toString())
//        spanString.setSpan(
//            ForegroundColorSpan(ContextCompat.getColor(this, color)),
//            0,
//            spanString.length,
//            0
//        )
//        menuItem.title = spanString
//    }

//    @SuppressLint("SuspiciousIndentation", "ResourceType")
//    private fun resetAllMenuItemsTextColor(navigationView: NavigationView) {
//        for (i in navigationView.menu.size.rangeTo(3))
//            setTextColorForMenuItem(navigationView.menu.getItem(i), Color.BLACK)
//    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        checkPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun goToProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}