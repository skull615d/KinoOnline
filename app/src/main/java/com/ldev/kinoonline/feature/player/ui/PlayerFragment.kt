package com.ldev.kinoonline.feature.player.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ldev.kinoonline.R
import com.ldev.kinoonline.databinding.FragmentPlayerBinding
import com.ldev.kinoonline.feature.base.hideSystemUI
import com.ldev.kinoonline.feature.player.service.PlayerService

class PlayerFragment : Fragment(R.layout.fragment_player) {
    companion object {
        const val KEY_MOVIE_URL = "movieUrl"
        const val MOVIE_NAME = "movieName"
        fun newInstance(movieUrl: String, movieName: String) = PlayerFragment().apply {
            arguments = bundleOf(Pair(KEY_MOVIE_URL, movieUrl), Pair(MOVIE_NAME, movieName))
        }
    }

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val movieUrl: String by lazy { requireArguments().getString(KEY_MOVIE_URL)!! }
    private val movieName: String by lazy { requireArguments().getString(MOVIE_NAME)!! }
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            when (service) {
                is PlayerService.PlayerServiceBinder -> {
                    binding.playerView.player = service.getPlayer()
                }
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {}
    }

    private lateinit var mMediaBrowserCompat: MediaBrowserCompat
    private val connectionCallback: MediaBrowserCompat.ConnectionCallback =
        object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {

                // The browser connected to the session successfully, use the token to create the controller
                super.onConnected()
                mMediaBrowserCompat.sessionToken.also { token ->
                    val mediaController = MediaControllerCompat(requireContext(), token)
                    MediaControllerCompat.setMediaController(requireActivity(), mediaController)
                }
                playPauseBuild()
            }

            override fun onConnectionFailed() {
                1
                super.onConnectionFailed()
            }

        }
    private val mControllerCallback = object : MediaControllerCompat.Callback() {
    }

    fun playPauseBuild() {
        val mediaController = MediaControllerCompat.getMediaController(requireActivity())
        binding.playerView.setOnClickListener {
            val state = mediaController.playbackState.state
            // if it is not playing then what are you waiting for ? PLAY !
            if (state == PlaybackStateCompat.STATE_PAUSED ||
                state == PlaybackStateCompat.STATE_STOPPED ||
                state == PlaybackStateCompat.STATE_NONE
            ) {

                mediaController.transportControls.playFromUri(Uri.parse(movieUrl), null)
            }
            // you are playing ? knock it off !
            else if (state == PlaybackStateCompat.STATE_PLAYING ||
                state == PlaybackStateCompat.STATE_BUFFERING ||
                state == PlaybackStateCompat.STATE_CONNECTING
            ) {
                mediaController.transportControls.pause()
            }
        }
        mediaController.registerCallback(mControllerCallback)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val componentName = ComponentName(requireContext(), PlayerService::class.java)
        // initialize the browser
        mMediaBrowserCompat = MediaBrowserCompat(
            requireContext(), componentName, //Identifier for the service
            connectionCallback,
            null
        )
        val intent = Intent(requireContext(), PlayerService.newInstance()::class.java)
        intent.putExtra(PlayerService.KEY_MOVIE_URL, movieUrl)
        intent.putExtra(PlayerService.MOVIE_NAME, movieName)
        requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun hideSystemUi() {
        hideSystemUI(requireActivity().window, binding.playerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unbindService(serviceConnection)
    }

    override fun onStart() {
        super.onStart()
        hideSystemUi()
        mMediaBrowserCompat.connect()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    override fun onPause() {
        super.onPause()
        hideSystemUi()
    }

    override fun onStop() {
        super.onStop()
        hideSystemUi()
        val controllerCompat = MediaControllerCompat.getMediaController(requireActivity())
        controllerCompat?.unregisterCallback(mControllerCallback)
        mMediaBrowserCompat.disconnect()
    }
}