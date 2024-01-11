package zechs.music.ui.albums.adapter

import androidx.recyclerview.widget.DiffUtil
import zechs.music.data.model.AlbumsResponse

class AlbumItemDiffCallback : DiffUtil.ItemCallback<AlbumsResponse>() {

    override fun areItemsTheSame(
        oldItem: AlbumsResponse, newItem: AlbumsResponse
    ) = oldItem.albumId == newItem.albumId

    override fun areContentsTheSame(
        oldItem: AlbumsResponse, newItem: AlbumsResponse
    ) = oldItem == newItem

}