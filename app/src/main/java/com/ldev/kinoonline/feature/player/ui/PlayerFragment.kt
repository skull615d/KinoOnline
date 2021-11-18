package com.ldev.kinoonline.feature.player.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }
}