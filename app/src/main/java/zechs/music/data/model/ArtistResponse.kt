package zechs.music.data.model

data class ArtistResponse(
    val _id: String,
    val albums: List<Album>,
    val artistId: String,
    val artistName: String
)
