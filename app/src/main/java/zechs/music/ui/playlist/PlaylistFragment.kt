package zechs.music.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis
import zechs.music.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {

    companion object {
        const val TAG = "PlaylistFragment"
    }

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlaylistBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
