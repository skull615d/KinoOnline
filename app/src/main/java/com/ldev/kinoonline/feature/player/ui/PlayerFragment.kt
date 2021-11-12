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
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : Fragment(R.layout.fragment_player) {
    companion object {

        fun newInstance(movieUrl: String) = PlayerFragment().apply {
            arguments = bundleOf(Pair(KEY_MOVIE_URL, movieUrl))
        }
    }

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val movieUrl: String by lazy { requireArguments().getString(KEY_MOVIE_URL)!! }
    private val viewModel by viewModel<PlayerViewModel>()
    private var player: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val intent = Intent(requireContext(), BackgroundService::class.java)
        intent.putExtra(KEY_MOVIE_URL, movieUrl)
        ContextCompat.startForegroundService(requireContext(),intent)*/
        player = ExoPlayer.Builder(requireContext()).build()
        viewModel.processUiEvent(UiEvent.OnSetMediaItem(MediaItem.fromUri(movieUrl)))
        binding.playerView.player = player
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.processUiEvent(
            UiEvent.OnSaveDataPlayer(
                player?.playWhenReady ?: true,
                player?.currentMediaItem,
                player?.currentPosition ?: 0L
            )
        )
        player?.release()
        player = null
    }

    private fun render(viewState: ViewState) {
        val mediaItem = viewState.mediaItem ?: MediaItem.EMPTY
        player?.apply {
            setMediaItem(mediaItem)
            playWhenReady = viewState.playWhenReady
            seekTo(viewState.playbackPosition)
            prepare()
        }
    }
}