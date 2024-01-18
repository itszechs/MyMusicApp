package zechs.music.ui.album_view.adapter

import androidx.recyclerview.widget.DiffUtil

class AlbumDataModelDiffCallback : DiffUtil.ItemCallback<AlbumModel>() {

    override fun areItemsTheSame(
        oldItem: AlbumModel,
        newItem: AlbumModel
    ): Boolean = when {

        oldItem is AlbumModel.Header && newItem
                is AlbumModel.Header && oldItem.albumId == newItem.albumId
        -> true

        oldItem is AlbumModel.Controls && newItem
                is AlbumModel.Controls && oldItem == newItem
        -> true
        oldItem is AlbumModel.Loading && newItem
                is AlbumModel.Loading && oldItem == newItem
        -> true

        oldItem is AlbumModel.DiscSeparator && newItem
                is AlbumModel.DiscSeparator && oldItem.discNumber == newItem.discNumber
        -> true

        oldItem is AlbumModel.Track && newItem
                is AlbumModel.Track && oldItem.fileId == newItem.fileId
        -> true

        else -> false
    }

    override fun areContentsTheSame(
        oldItem: AlbumModel,
        newItem: AlbumModel
    ) = oldItem == newItem

}