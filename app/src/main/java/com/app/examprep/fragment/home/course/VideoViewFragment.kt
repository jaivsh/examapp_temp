package com.app.examprep.fragment.home.course

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.app.examprep.R
import com.app.examprep.databinding.FragmentQuizBinding
import com.app.examprep.databinding.FragmentVideoviewBinding
import com.app.examprep.others.Constants
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class VideoViewFragment : Fragment(R.layout.fragment_videoview) {

    private lateinit var binding : FragmentVideoviewBinding
    lateinit var exoPlayer: ExoPlayer
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVideoviewBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setExoPlayer()


    }

    private fun setExoPlayer(){
        val data = Constants.curUrl
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        val mediaItem = MediaItem.fromUri(data)
        exoPlayer.setMediaItem(mediaItem)
        binding.exoView.player = exoPlayer
        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        exoPlayer.stop()
    }


}