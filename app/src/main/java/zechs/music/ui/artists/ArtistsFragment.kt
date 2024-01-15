package zechs.music.ui.artists

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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch
import zechs.music.databinding.FragmentListBinding
import zechs.music.ui.artists.adapter.ArtistsAdapter
import zechs.music.utils.ext.doTransition
import zechs.music.utils.state.Resource

class ArtistsFragment : Fragment() {

    companion object {
        const val TAG = "ArtistsFragment"
    }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val artistsAdapter by lazy { ArtistsAdapter(onClick = {}) }

    private val viewModel by activityViewModels<ArtistsViewModel>()

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
                viewModel.artists.collect { response ->
                    when (response) {
                        is Resource.Success -> response.data?.let {
                            binding.root.doTransition(MaterialFadeThrough())
                            artistsAdapter.submitList(it)
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

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            /* context */ context,
            /* orientation */ RecyclerView.VERTICAL,
            /* reverseLayout */ false
        )
        binding.rvList.apply {
            adapter = artistsAdapter
            layoutManager = linearLayoutManager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
