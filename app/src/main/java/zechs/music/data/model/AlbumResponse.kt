package zechs.music.data.model

import zechs.music.utils.AlbumArtHelper

data class AlbumResponse(
    val _id: String,
    val albumId: String,
    val albumName: String,
    val artistId: String,
    val tracks: List<Track>,
    val year: Int
) {

    fun albumArt() = AlbumArtHelper.withArt(albumId)
    fun isMultiDisc() = tracks.any { it.discNumber > 1 }

}
