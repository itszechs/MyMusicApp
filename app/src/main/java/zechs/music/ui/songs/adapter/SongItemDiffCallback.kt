package zechs.music.ui.songs.adapter

import androidx.recyclerview.widget.DiffUtil
import zechs.music.data.model.SongsResponse

class SongItemDiffCallback : DiffUtil.ItemCallback<SongsResponse>() {

    override fun areItemsTheSame(
        oldItem: SongsResponse, newItem: SongsResponse
    ) = oldItem.fileId == newItem.fileId

    override fun areContentsTheSame(
        oldItem: SongsResponse, newItem: SongsResponse
    ) = oldItem == newItem

}