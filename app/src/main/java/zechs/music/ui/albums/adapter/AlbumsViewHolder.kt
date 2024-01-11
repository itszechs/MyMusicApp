package zechs.music.ui.albums.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zechs.music.R
import zechs.music.data.model.AlbumsResponse
import zechs.music.databinding.ItemAlbumBinding
import zechs.music.utils.AlbumArtHelper

class AlbumsViewHolder(
    private val itemBinding: ItemAlbumBinding,
    private val albumsAdapter: AlbumsAdapter
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(album: AlbumsResponse) {
        itemBinding.apply {
            tvAlbumName.text = album.albumName
            tvArtistName.text = album.artistName

            Glide.with(ivAlbumArt)
                .load(album.albumArt().setSize(AlbumArtHelper.Size.LARGE).build())
                .placeholder(R.drawable.thumbnail)
                .override(500)
                .into(ivAlbumArt)
            root.setOnClickListener { albumsAdapter.onClick.invoke(album) }
        }
    }
}