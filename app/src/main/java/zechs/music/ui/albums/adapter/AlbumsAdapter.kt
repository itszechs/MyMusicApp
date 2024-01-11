package zechs.music.ui.albums.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import zechs.music.data.model.AlbumsResponse
import zechs.music.databinding.ItemAlbumBinding

class AlbumsAdapter(
    val onClick: (AlbumsResponse) -> Unit
) : ListAdapter<AlbumsResponse, AlbumsViewHolder>(AlbumItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = AlbumsViewHolder(
        itemBinding = ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ),
        albumsAdapter = this
    )

    override fun onBindViewHolder(
        holder: AlbumsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

}