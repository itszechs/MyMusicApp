package zechs.music.data.model

data class AlbumsResponse(
    val albumId: String,
    val albumName: String,
    val artistId: String,
    val artistName: String,
    val year: Int
)