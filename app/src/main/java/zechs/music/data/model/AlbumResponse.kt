package zechs.music.data.model

data class AlbumResponse(
    val _id: String,
    val albumId: String,
    val albumName: String,
    val artistId: String,
    val tracks: List<Track>,
    val year: Int
)
