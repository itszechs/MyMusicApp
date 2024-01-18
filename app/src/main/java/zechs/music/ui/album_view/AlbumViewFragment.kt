package zechs.music.ui.album_view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch
import zechs.music.databinding.FragmentAlbumViewBinding
import zechs.music.ui.album_view.adapter.AlbumAdapter
import zechs.music.ui.album_view.adapter.AlbumClickListener
import zechs.music.ui.album_view.adapter.AlbumModel
import zechs.music.utils.state.Resource


class AlbumViewFragment : Fragment() {

    companion object {
        const val TAG = "AlbumViewFragment"
    }

    private var _binding: FragmentAlbumViewBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<AlbumViewFragmentArgs>()
    private val viewModel by activityViewModels<AlbumViewViewModel>()

    private val albumAdapter by lazy { AlbumAdapter(albumClickListener) }

    private val albumClickListener = object : AlbumClickListener {
        override fun onClickTrack(track: AlbumModel.Track) {
        }

        override fun onClickPlay(tracks: List<AlbumModel.Track>) {
        }

        override fun onClickShuffle(tracks: List<AlbumModel.Track>) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward = */ true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward = */ false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward = */ false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumViewBinding.bind(view)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        setupRecyclerView()
        albumObserver()

        if (!viewModel.hasLoaded || viewModel.loadedAlbumId != args.albumId) {
            Log.d(TAG, "Loading album: ${args.albumName}")
            viewModel.getAlbum(
                albumId = args.albumId,
                albumName = args.albumName,
                artistName = args.albumArtistName,
                releaseYear = args.releaseYear
            )
        }
    }

    private fun albumObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.albums.collect { state ->
                    when (state) {
                        is Resource.Success -> {
                            isLoading(false)
                            albumAdapter.submitList(state.data)
                        }

                        is Resource.Error -> {
                            isLoading(false)
                            Snackbar.make(
                                binding.root,
                                state.message!!,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                        is Resource.Loading -> isLoading(true)
                    }
                }
            }
        }
    }

    private fun isLoading(hide: Boolean) {
        binding.apply {
            loading.isInvisible = !hide
            rvList.isInvisible = hide
        }
    }


    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            /* context */ context,
            /* spanCount */ RecyclerView.VERTICAL,
            /* reverseLayout */ false
        )
        binding.rvList.apply {
            adapter = albumAdapter
            layoutManager = linearLayoutManager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
