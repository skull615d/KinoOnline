package com.ldev.kinoonline.feature.player.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.ldev.kinoonline.R
import com.ldev.kinoonline.databinding.FragmentPlayerBinding
import com.ldev.kinoonline.feature.player.KEY_MOVIE_URL
import org.koin.android.ext.android.inject

class PlayerFragment : Fragment(R.layout.fragment_player) {
    companion object {

        fun newInstance(movieUrl: String) = PlayerFragment().apply {
            arguments = bundleOf(Pair(KEY_MOVIE_URL, movieUrl))
        }
    }

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val movieUrl: String by lazy { requireArguments().getString(KEY_MOVIE_URL)!! }
    private val player by inject<ExoPlayer>()
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val intent = Intent(requireContext(), BackgroundService::class.java)
        intent.putExtra(KEY_MOVIE_URL, movieUrl)
        ContextCompat.startForegroundService(requireContext(),intent)*/
        initializePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun initializePlayer() {

        player.also {
            binding.playerView.player = it
            val mediaItem = MediaItem.fromUri(movieUrl)
            it.setMediaItem(mediaItem)
            /*it.playWhenReady = playWhenReady
            it.seekTo(currentWindow, playbackPosition)*/
            it.prepare()
        }
    }

    private fun releasePlayer() {
        player.run {
            /*playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            playWhenReady = this.playWhenReady*/
            release()
        }
    }
}