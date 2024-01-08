package zechs.music.ui.home

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import zechs.music.ui.albums.AlbumsFragment
import zechs.music.ui.artists.ArtistsFragment
import zechs.music.ui.playlist.PlaylistFragment
import zechs.music.ui.songs.SongsFragment

class FragmentAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {

    private val tabs = listOf(
        PlaylistFragment(),
        SongsFragment(),
        AlbumsFragment(),
        ArtistsFragment()
    )

    override fun getItemCount() = tabs.size
    override fun createFragment(position: Int) = tabs[position]

}