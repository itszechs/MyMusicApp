package zechs.music.ui.album_view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import zechs.music.R
import zechs.music.databinding.ItemAlbumHeaderBinding
import zechs.music.databinding.ItemAlbumPlaybackBinding
import zechs.music.databinding.ItemAlbumTrackBinding
import zechs.music.databinding.ItemDiscSeparatorBinding
import zechs.music.databinding.ItemLoadingBinding
import zechs.music.ui.album_view.adapter.AlbumViewHolder.AlbumTrackViewHolder
import zechs.music.ui.album_view.adapter.AlbumViewHolder.ControlsViewHolder
import zechs.music.ui.album_view.adapter.AlbumViewHolder.DiscSeparatorViewHolder
import zechs.music.ui.album_view.adapter.AlbumViewHolder.HeaderViewHolder
import zechs.music.ui.album_view.adapter.AlbumViewHolder.LoadingViewHolder

class AlbumAdapter(
    private val albumClickListener: AlbumClickListener
) : ListAdapter<AlbumModel, AlbumViewHolder>(AlbumDataModelDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AlbumViewHolder {

        val headerViewHolder = HeaderViewHolder(
            itemBinding = ItemAlbumHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

        val loadingViewHolder = LoadingViewHolder(
            itemBinding = ItemLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

        val controlsViewHolder = ControlsViewHolder(
            itemBinding = ItemAlbumPlaybackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), albumClickListener = albumClickListener
        )

        val discSeparatorViewHolder = DiscSeparatorViewHolder(
            itemBinding = ItemDiscSeparatorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

        val albumTrackViewHolder = AlbumTrackViewHolder(
            itemBinding = ItemAlbumTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), albumClickListener = albumClickListener
        )

        return when (viewType) {
            R.layout.item_album_header -> headerViewHolder
            R.layout.item_loading -> loadingViewHolder
            R.layout.item_album_playback -> controlsViewHolder
            R.layout.item_disc_separator -> discSeparatorViewHolder
            R.layout.item_album_track -> albumTrackViewHolder
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(item as AlbumModel.Header)
            is LoadingViewHolder -> item as AlbumModel.Loading
            is ControlsViewHolder -> holder.bind(item as AlbumModel.Controls)
            is DiscSeparatorViewHolder -> holder.bind(item as AlbumModel.DiscSeparator)
            is AlbumTrackViewHolder -> holder.bind(item as AlbumModel.Track )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AlbumModel.Header -> R.layout.item_album_header
            is AlbumModel.Loading -> R.layout.item_loading
            is AlbumModel.Controls -> R.layout.item_album_playback
            is AlbumModel.DiscSeparator -> R.layout.item_disc_separator
            is AlbumModel.Track -> R.layout.item_album_track
        }
    }
}