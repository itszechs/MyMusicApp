package zechs.music.data.model

import zechs.music.utils.AlbumArtHelper

data class AlbumsResponse(
    val albumId: String,
    val albumName: String,
    val artistId: String,
    val artistName: String,
    val year: Int
) {

    fun albumArt() = AlbumArtHelper.withArt(albumId)

}