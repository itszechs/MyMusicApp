package zechs.music.ui.artists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import zechs.music.data.model.ArtistsResponse
import zechs.music.databinding.ItemArtistBinding

class ArtistsAdapter(
    val onClick: (ArtistsResponse) -> Unit
) : ListAdapter<ArtistsResponse, ArtistsViewHolder>(ArtistItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = ArtistsViewHolder(
        itemBinding = ItemArtistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ),
        artistsAdapter = this
    )

    override fun onBindViewHolder(
        holder: ArtistsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

}