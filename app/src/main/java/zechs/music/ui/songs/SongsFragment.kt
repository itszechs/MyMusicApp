package zechs.music.ui.songs

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch
import zechs.music.databinding.FragmentListBinding
import zechs.music.ui.songs.adapter.SongsAdapter
import zechs.music.utils.ext.doTransition
import zechs.music.utils.state.Resource


class SongsFragment : Fragment() {

    companion object {
        const val TAG = "SongsFragment"
    }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val songsAdapter by lazy {
        SongsAdapter(requestManager = Glide.with(this@SongsFragment),
            onSongClickListener = { song ->

            })
    }

    private val viewModel by activityViewModels<SongsViewModel>()

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
                viewModel.songs.collect { response ->
                    when (response) {
                        is Resource.Success -> response.data?.let {
                            binding.root.doTransition(MaterialFadeThrough())
                            songsAdapter.submitList(it)
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
                val layoutManager = binding.rvList.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition() + 1
                val itemCount = layoutManager.itemCount - 5

                if (visibleItemCount >= itemCount && !viewModel.isLoading && !viewModel.isLastPage) {
                    Log.d(
                        "onScrolled",
                        "visibleItemCount=$visibleItemCount, itemCount=$itemCount, " +
                                "isLoading=${viewModel.isLoading} isLastPage=${viewModel.isLastPage}"
                    )
                    viewModel.fetchSongs()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            /* context */ context,
            /* orientation */ RecyclerView.VERTICAL,
            /* reverseLayout */ false
        )
        binding.rvList.apply {
            adapter = songsAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(scrollListener)
            addOnScrollListener(
                RecyclerViewPreloader(
                    Glide.with(this@SongsFragment),
                    songsAdapter, songsAdapter, 10
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
