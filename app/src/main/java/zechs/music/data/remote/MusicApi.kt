package zechs.music.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import zechs.music.data.model.AlbumResponse
import zechs.music.data.model.AlbumsResponse
import zechs.music.data.model.ApiResponse
import zechs.music.data.model.ArtistResponse
import zechs.music.data.model.ArtistsResponse
import zechs.music.data.model.SongsResponse

interface MusicApi {

    @GET("/api/v1/songs")
    suspend fun getSongs(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<ApiResponse<SongsResponse>>

    @GET("/api/v1/albums")
    suspend fun getAlbums(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<ApiResponse<AlbumsResponse>>

    @GET("/api/v1/albums/{albumId}")
    suspend fun getAlbum(
        @Path("albumId") albumId: String
    ): Response<AlbumResponse>

    @GET("/api/v1/artists")
    suspend fun getArtists(): Response<List<ArtistsResponse>>

    @GET("/api/v1/artists/{artistId}")
    suspend fun getArtist(
        @Path("artistId") artistId: String
    ): Response<ArtistResponse>

}