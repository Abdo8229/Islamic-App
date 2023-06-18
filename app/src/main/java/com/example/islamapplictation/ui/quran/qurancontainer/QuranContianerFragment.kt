package com.example.islamapplictation.ui.quran.qurancontainer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.islamapplictation.R
import com.example.islamapplictation.databinding.FragmentQuranContianerBinding
import com.example.islamapplictation.prayersnotifivcation.PrayersPreferences
import com.google.android.material.snackbar.Snackbar


class QuranContianerFragment : Fragment() {
    private val TAG = "QuranContianerFragment"
    private val exoPlayer: ExoPlayer by lazy { ExoPlayer.Builder(requireContext()).build() }
    private var flag: Boolean = false
    private val argument: QuranContianerFragmentArgs by lazy {
        QuranContianerFragmentArgs.fromBundle(
            requireArguments()
        )
    }
    private val fragmentStateAdapter: FragmentStateAdapter by lazy {
        ScreenSlidePagerAdapter(
            requireActivity()
        )
    }
    lateinit var binding: FragmentQuranContianerBinding


    private companion object {
        var soraNumber: Int = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuranContianerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpQuranContainer.adapter = fragmentStateAdapter
        binding.vpQuranContainer.setCurrentItem(604 - argument.startPage, false)
        soraNumber = argument.soraNumber
        binding.playPauseContainerFragment.setOnClickListener {
            if (!isNetworkAvailable()) {
                playSnackBar()
                return@setOnClickListener
            }
            flag = if (!flag) {
                binding.playPauseContainerFragment.setImageResource(R.drawable.round_pause_24)
                Log.d(TAG, "onViewCreated: ")
                playAudio()
                true
            } else {
                binding.playPauseContainerFragment.setImageResource(R.drawable.round_play_arrow_24)
                pauseAudio()
                false
            }
        }
        binding.skipNextContainerFragment.setOnClickListener {
            if (!isNetworkAvailable()) {
                playSnackBar()
                return@setOnClickListener
            }
            skipNext()
        }
        binding.skipPreviousContainerFragment.setOnClickListener {
            if (!isNetworkAvailable()) {
                playSnackBar()
                return@setOnClickListener
            }
            skipPrevious()
        }
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if (!isNetworkAvailable())
                   playSnackBar()
                if (!isPlaying) {
                    binding.playPauseContainerFragment.setImageResource(R.drawable.round_play_arrow_24)
                    flag = false
                } else {
                    return
                }
            }

        })
    }

    private fun playSnackBar() {
        val snackbar =
            Snackbar.make(binding.root, "Please connect to network", Snackbar.LENGTH_SHORT)
        snackbar.setAction("Ok") {
          snackbar.dismiss()
        }
        snackbar.setActionTextColor(Color.BLUE)
        snackbar.setTextColor(Color.RED)

        snackbar.show()
    }

    private fun skipPrevious() {
        pauseAudio()
        if (soraNumber > 1) {
            soraNumber -= 1
            playAudio()
        } else {
            soraNumber = 114
            playAudio()
        }
        flag = true
        binding.playPauseContainerFragment.setImageResource(R.drawable.round_pause_24)
    }

    private fun skipNext() {
        pauseAudio()
        if (soraNumber < 114) {
            soraNumber += 1
            playAudio()
        } else {
            soraNumber = 1
            playAudio()
        }
        flag = true
        binding.playPauseContainerFragment.setImageResource(R.drawable.round_pause_24)
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.release()

        onDestroy()
    }

    private fun playAudio() {
        if (!exoPlayer.isPlaying) {
            val audioUrl =
                "https://cdn.islamic.network/quran/audio-surah/128/${PrayersPreferences(requireContext()).quranVoiceIdentifier}/${soraNumber}.mp3"
            exoPlayer.also { exoPlayer ->
                val mediaPlayer = MediaItem.fromUri(audioUrl)
                exoPlayer.setMediaItem(mediaPlayer)
                exoPlayer.prepare()
            }
            exoPlayer.play()
        }

    }

    private fun pauseAudio() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            exoPlayer.stop()
        } else {
            Toast.makeText(requireContext(), "Audio has not played ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}