package zechs.music.ui.artists.adapter

import androidx.recyclerview.widget.RecyclerView
import zechs.music.data.model.ArtistsResponse
import zechs.music.databinding.ItemArtistBinding

class ArtistsViewHolder(
    private val itemBinding: ItemArtistBinding,
    val artistsAdapter: ArtistsAdapter
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(artist: ArtistsResponse) {
        itemBinding.apply {
            tvArtistName.text = artist.artistName
            root.setOnClickListener { artistsAdapter.onClick.invoke(artist) }
        }
    }
}