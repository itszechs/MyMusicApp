package zechs.music.data.repository

import retrofit2.Response
import zechs.music.data.model.AlbumResponse
import zechs.music.data.model.AlbumsResponse
import zechs.music.data.model.ApiResponse
import zechs.music.data.model.ArtistResponse
import zechs.music.data.model.ArtistsResponse
import zechs.music.data.model.SongsResponse
import zechs.music.data.remote.MusicApi
import zechs.music.utils.Constants.Companion.RESULT_SIZE
import zechs.music.utils.runInTryCatch
import zechs.music.utils.state.Resource

class MusicRepository(
    private val musicApi: MusicApi
) {

    private suspend fun <T> executeRequest(
        request: suspend () -> Response<T>
    ): Resource<T> {
        return runInTryCatch(
            tryBlock = {
                val response = request.invoke()
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    throw Exception(
                        response.message().ifBlank { "${response.code()} Unknown error!" }
                    )
                }
            },
            catchBlock = { err ->
                err.printStackTrace()
                Resource.Error(err.message?.ifBlank { "Unknown error!" } ?: "Unknown error!")
            }
        )
    }

    suspend fun getSongs(
        page: Int,
        limit: Int = RESULT_SIZE
    ): Resource<ApiResponse<SongsResponse>> {
        return executeRequest { musicApi.getSongs(page, limit) }
    }

    suspend fun getAlbums(
        page: Int,
        limit: Int = RESULT_SIZE
    ): Resource<ApiResponse<AlbumsResponse>> {
        return executeRequest { musicApi.getAlbums(page, limit) }
    }

    suspend fun getAlbum(albumId: String): Resource<AlbumResponse> {
        return executeRequest { musicApi.getAlbum(albumId) }
    }

    suspend fun getArtists(): Resource<List<ArtistsResponse>> {
        return executeRequest { musicApi.getArtists() }
    }

    suspend fun getArtist(artistId: String): Resource<ArtistResponse> {
        return executeRequest { musicApi.getArtist(artistId) }
    }
}