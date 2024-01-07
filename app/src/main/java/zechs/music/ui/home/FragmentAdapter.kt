package zechs.music.ui.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import zechs.music.ui.albums.AlbumsFragment
import zechs.music.ui.artists.ArtistsFragment
import zechs.music.ui.playlist.PlaylistFragment
import zechs.music.ui.songs.SongsFragment

class FragmentAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlaylistFragment()
            1 -> SongsFragment()
            2 -> AlbumsFragment()
            3 -> ArtistsFragment()
            else -> PlaylistFragment()
        }
    }

}