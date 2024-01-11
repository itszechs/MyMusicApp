package zechs.music.ui.albums

import android.content.res.Configuration
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch
import zechs.music.databinding.FragmentListBinding
import zechs.music.ui.albums.adapter.AlbumsAdapter
import zechs.music.utils.ext.doTransition
import zechs.music.utils.state.Resource

class AlbumsFragment : Fragment() {

    companion object {
        const val TAG = "AlbumsFragment"
    }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val albumsAdapter by lazy {
        AlbumsAdapter(onClick = {})
    }

    private val viewModel by activityViewModels<AlbumsViewModel>()

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
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)

        setupRecyclerView()
        songsObserver()
    }

    private fun songsObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.albums.collect { response ->
                    when (response) {
                        is Resource.Success -> response.data?.let {
                            binding.root.doTransition(MaterialFadeThrough())
                            albumsAdapter.submitList(it)
                            isLoading(false)
                        }

                        is Resource.Error -> {
                            Log.d(TAG, "Error: ${response.message}")
                            showSnackBar(response.message)
                            binding.rvList.isInvisible = true
                        }

                        is Resource.Loading -> if (!viewModel.hasLoaded) {
                            isLoading(true)
                        }
                    }
                }
            }
        }

    }

    private fun showSnackBar(message: String?) {
        Snackbar.make(
            binding.root,
            message ?: "Something went wrong!",
            Snackbar.LENGTH_SHORT
        ).show()
    }


    private fun isLoading(hide: Boolean) {
        binding.apply {
            loading.isInvisible = !hide
            rvList.isInvisible = hide
        }
    }


    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val layoutManager = binding.rvList.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition() + 1
                val itemCount = layoutManager.itemCount

                if (visibleItemCount == itemCount && !viewModel.isLoading && !viewModel.isLastPage) {
                    Log.d(
                        "onScrolled",
                        "visibleItemCount=$visibleItemCount, itemCount=$itemCount, " +
                                "isLoading=${viewModel.isLoading} isLastPage=${viewModel.isLastPage}"
                    )
                    viewModel.fetchAlbums()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val screenOrientation = resources.configuration.orientation
        val spanCount = if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) 2 else 5
        val gridLayoutManager = GridLayoutManager(
            /* context */ context,
            /* spanCount */ spanCount
        )
        binding.rvList.apply {
            adapter = albumsAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(scrollListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rvList.removeOnScrollListener(scrollListener)
        binding.rvList.adapter = null
        _binding = null
    }

}
