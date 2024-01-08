package zechs.music.ui.songs.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import zechs.music.R
import zechs.music.data.model.SongsResponse
import zechs.music.databinding.ItemSongBinding
import zechs.music.utils.AlbumArtHelper
import java.util.Collections

class SongsAdapter(
    private val requestManager: RequestManager,
    val onSongClickListener: (SongsResponse) -> Unit
) : ListAdapter<SongsResponse, SongsViewHolder>(SongItemDiffCallback()),
    ListPreloader.PreloadModelProvider<SongsResponse>,
    ListPreloader.PreloadSizeProvider<SongsResponse> {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = SongsViewHolder(
        itemBinding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ), this
    )

    override fun onBindViewHolder(
        holder: SongsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    override fun getPreloadItems(position: Int): MutableList<SongsResponse> {
        return Collections.singletonList(getItem(position));
    }

    override fun getPreloadRequestBuilder(
        item: SongsResponse
    ): RequestBuilder<Drawable> {
        return requestManager
            .load(item.albumArt().setSize(AlbumArtHelper.Size.LARGE).build())
            .placeholder(R.drawable.thumbnail)
    }

    override fun getPreloadSize(
        item: SongsResponse,
        adapterPosition: Int,
        perItemPosition: Int
    ): IntArray {
        return intArrayOf(100, 100)
    }

}