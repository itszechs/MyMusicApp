package zechs.music.ui.album_view.adapter

import androidx.annotation.Keep
import zechs.music.utils.AlbumArtHelper

sealed class AlbumModel {

    @Keep
    data class Header(
        val albumId: String,
        val albumArt: AlbumArtHelper,
        val albumName: String,
        val artistName: String,
        val releaseYear: Int
    ) : AlbumModel()

    @Keep
    data class Controls(
        val tracks: List<Track>
    ) : AlbumModel()

    object Loading : AlbumModel()

    @Keep
    data class DiscSeparator(
        val discNumber: Int
    ) : AlbumModel()

    @Keep
    data class Track(
        val fileId: String,
        val trackName: String,
        val artistName: String,
        val trackNumber: Int,
        val discNumber: Int,
        val duration: Int,
        val albumArt: AlbumArtHelper
    ) : AlbumModel() {
        fun humanReadableDuration(): String {
            val hours = duration / 3600
            val minutes = (duration % 3600) / 60
            val seconds = duration % 60
            return if (hours > 0) {
                String.format("%d:%02d:%02d", hours, minutes, seconds)
            } else {
                String.format("%d:%02d", minutes, seconds)
            }
        }
    }

}
