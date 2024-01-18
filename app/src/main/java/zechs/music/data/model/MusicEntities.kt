package zechs.music.data.model

import zechs.music.ui.album_view.adapter.AlbumModel
import zechs.music.utils.AlbumArtHelper


data class Album(
    val _id: String,
    val albumId: String,
    val albumName: String,
    val year: Int
)

data class Track(
    val _id: String,
    val albumId: String,
    val artistId: String,
    val artistName: String,
    val discNumber: Int,
    val duration: Int,
    val fileExtension: String,
    val fileId: String,
    val fileSize: Int,
    val recordingId: String,
    val sampleRate: Int,
    val trackName: String,
    val trackNumber: Int
) {

    fun albumArt() = AlbumArtHelper.withArt(albumId)

    fun toAlbumModelTrack() = AlbumModel.Track(
        fileId = fileId,
        trackName = trackName,
        artistName = artistName,
        trackNumber = trackNumber,
        discNumber = discNumber,
        duration = duration,
        albumArt = albumArt()
    )
}

data class Artist(
    val _id: String,
    val artistId: String,
    val artistName: String
)