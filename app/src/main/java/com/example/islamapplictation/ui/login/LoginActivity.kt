package com.example.islamapplictation.ui.login
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.islamapplictation.R
import com.example.islamapplictation.databinding.ActivityLoginBinding
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import java.time.Duration

class LoginActivity : AppCompatActivity() {
    private val binding : ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel:LoginViewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }
    private lateinit var lottie  : LottieAnimationView
    private  val TAG = "LoginActivity"
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lottie = findViewById(R.id.lottie_view)
        lottie.loop(true)
        val d = Dela()
        d.start()
        lottie.loop(false)
        lottie.visibility = View.GONE
//        content.viewTreeObserver.addOnPreDrawListener (object : OnPreDrawListener{
//            override fun onPreDraw(): Boolean {
//             return   if (viewModel!=null){
//                 content.viewTreeObserver.removeOnPreDrawListener(this)
//                 true
//             }else{
//                    false
//                }
//            }
//
//        })
//        splashScreen.setOnExitAnimationListener {
//            val slideUp = ObjectAnimator.ofFloat(
//                splashScreenView,
//                View.TRANSLATION_Y,
//                0f,
//                -splashScreenView.height.toFloat()
//            )
//            slideUp.interpolator = AnticipateInterpolator()
//            slideUp.duration = 200L
//
//            // Call SplashScreenView.remove at the end of your custom animation.
//            slideUp.doOnEnd { splashScreenView.remove() }
//
//            // Run your animation.
//            slideUp.start()
//
//        }
    }
inner class  Dela :Thread(){
    override  fun run() {
        super.run()
        synchronized(this){
            try {
               sleep(3000L)
            }catch (e : InterruptedException){
                e.printStackTrace()
            }
        }
    }
}
}
