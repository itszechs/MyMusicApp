package zechs.music.ui.songs.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zechs.music.R
import zechs.music.data.model.SongsResponse
import zechs.music.databinding.ItemSongBinding
import zechs.music.utils.AlbumArtHelper

class SongsViewHolder(
    private val itemBinding: ItemSongBinding,
    private val songsAdapter: SongsAdapter
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(song: SongsResponse) {
        itemBinding.apply {
            tvSongName.text = song.trackName
            tvArtistName.text = song.artistName

            Glide.with(ivAlbumArt)
                .load(song.albumArt().setSize(AlbumArtHelper.Size.LARGE).build())
                .placeholder(R.drawable.thumbnail)
                .override(100)
                .into(ivAlbumArt)
            root.setOnClickListener { songsAdapter.onSongClickListener(song) }
        }
    }
}