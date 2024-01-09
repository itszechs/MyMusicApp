package zechs.music.utils

import zechs.music.utils.Constants.Companion.MUSIC_API

class AlbumArtHelper private constructor(
    private val albumArt: String
) {

    enum class Size(val size: String) {
        SMALL("small"),
        DEFAULT("default"),
        LARGE("large"),
        ORIGINAL("original")
    }

    companion object {
        fun withArt(albumArt: String): AlbumArtHelper {
            return AlbumArtHelper(albumArt)
        }
    }

    private var size: Size = Size.DEFAULT

    fun setSize(size: Size): AlbumArtHelper {
        this.size = size
        return this
    }

    fun build(): String {
        val albumArtLink = "$MUSIC_API/api/v1/albums/art/$albumArt"
        return "$albumArtLink?size=${size.size}"
    }
}
