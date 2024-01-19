package zechs.music.ui.album_view.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import zechs.music.R
import zechs.music.databinding.ItemAlbumHeaderBinding
import zechs.music.databinding.ItemAlbumPlaybackBinding
import zechs.music.databinding.ItemAlbumTrackBinding
import zechs.music.databinding.ItemDiscSeparatorBinding
import zechs.music.databinding.ItemLoadingBinding
import zechs.music.utils.AlbumArtHelper

sealed class AlbumViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class HeaderViewHolder(
        private val itemBinding: ItemAlbumHeaderBinding
    ) : AlbumViewHolder(itemBinding) {

        fun bind(item: AlbumModel.Header) {
            itemBinding.apply {
                Glide
                    .with(ivAlbumArt)
                    .load(item.albumArt.setSize(AlbumArtHelper.Size.LARGE).build())
                    .placeholder(R.drawable.thumbnail)
                    .into(ivAlbumArt)

                tvAlbumName.text = item.albumName
                tvArtistName.text = item.artistName
                tvYear.text = item.releaseYear.toString()
            }
        }
    }

    class LoadingViewHolder(
        itemBinding: ItemLoadingBinding,
    ) : AlbumViewHolder(itemBinding)

    class ControlsViewHolder(
        private val itemBinding: ItemAlbumPlaybackBinding,
        private val albumClickListener: AlbumClickListener
    ) : AlbumViewHolder(itemBinding) {
        fun bind(item: AlbumModel.Controls) {
            itemBinding.apply {
                btnPlayAlbum.setOnClickListener {
                    albumClickListener.onClickPlay(item.tracks)
                }
                btnShuffle.setOnClickListener {
                    albumClickListener.onClickShuffle(item.tracks)
                }
            }
        }
    }

    class DiscSeparatorViewHolder(
        private val itemBinding: ItemDiscSeparatorBinding,
    ) : AlbumViewHolder(itemBinding) {
        fun bind(item: AlbumModel.DiscSeparator) {
            itemBinding.tvDiscNumber.apply {
                text = context.getString(R.string.disc_number, item.discNumber)
            }
        }
    }

    class AlbumTrackViewHolder(
        private val itemBinding: ItemAlbumTrackBinding,
        private val albumClickListener: AlbumClickListener
    ) : AlbumViewHolder(itemBinding) {
        fun bind(item: AlbumModel.Track) {
            itemBinding.apply {
                tvTrackNumber.text = item.trackNumber.toString()
                tvTrackName.text = item.trackName
                tvArtistName.text = item.artistName
                tvDuration.text = item.humanReadableDuration()
                root.setOnClickListener { albumClickListener.onClickTrack(item) }
            }
        }
    }

}