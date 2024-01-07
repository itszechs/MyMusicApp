package zechs.music.data.model

data class SongsResponse(
    val _id: String,
    val albumId: String,
    val albumName: String,
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
)