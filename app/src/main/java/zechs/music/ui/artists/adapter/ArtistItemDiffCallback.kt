package zechs.music.ui.artists.adapter

import androidx.recyclerview.widget.DiffUtil
import zechs.music.data.model.ArtistsResponse

class ArtistItemDiffCallback : DiffUtil.ItemCallback<ArtistsResponse>() {

    override fun areItemsTheSame(
        oldItem: ArtistsResponse, newItem: ArtistsResponse
    ) = oldItem.artistId == newItem.artistId

    override fun areContentsTheSame(
        oldItem: ArtistsResponse, newItem: ArtistsResponse
    ) = oldItem == newItem

}