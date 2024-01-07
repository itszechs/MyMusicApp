package zechs.music.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis
import zechs.music.R
import zechs.music.databinding.FragmentHomeBinding
import zechs.music.ui.main.MainActivity

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.viewPager.adapter = FragmentAdapter(requireActivity() as MainActivity)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.playlists)
                1 -> getString(R.string.songs)
                2 -> getString(R.string.albums)
                3 -> getString(R.string.artists)
                else -> getString(R.string.playlists)
            }
            Log.d(TAG, "TabNavigation: $position ${tab.text}")
        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
