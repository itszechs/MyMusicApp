package zechs.music.ui.album_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zechs.music.data.model.AlbumResponse
import zechs.music.data.repository.MusicRepository
import zechs.music.ui.album_view.adapter.AlbumModel
import zechs.music.utils.AlbumArtHelper
import zechs.music.utils.state.Resource
import javax.inject.Inject

@HiltViewModel
class AlbumViewViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {

    private val _albums = MutableStateFlow<Resource<List<AlbumModel>>>(Resource.Loading())
    val albums = _albums.asStateFlow()

    private val isPreDataLoaded = false

    var hasLoaded = false
        private set

    var loadedAlbumId = ""
        private set

    var tracks: List<AlbumModel.Track> = listOf()
        private set

    fun getAlbum(
        albumId: String,
        albumName: String,
        artistName: String,
        releaseYear: Int,
    ) = viewModelScope.launch(Dispatchers.IO) {
        _albums.value = Resource.Loading()
        if (!isPreDataLoaded) {
            val art = AlbumArtHelper.withArt(albumId)
            val preload = mutableListOf<AlbumModel>()
            preload.add(AlbumModel.Header(albumId, art, albumName, artistName, releaseYear))
            preload.add(AlbumModel.Loading)
            _albums.value = Resource.Success(preload.toList())
        }
        val response = musicRepository.getAlbum(albumId)
        if (response is Resource.Success) {
            hasLoaded = true
            handleAlbumResponse(response.data!!, artistName)
        } else {
            _albums.value = Resource.Error(response.message!!)
        }
    }

    private fun handleAlbumResponse(
        album: AlbumResponse,
        artistName: String
    ) {
        val albumModel = mutableListOf<AlbumModel>()
        albumModel.add(
            AlbumModel.Header(
                albumId = album.albumId,
                albumArt = album.albumArt(),
                albumName = album.albumName,
                artistName = artistName,
                releaseYear = album.year
            )
        )

        val albumTracks = album.tracks.map { it.toAlbumModelTrack() }
        albumModel.add(AlbumModel.Controls(albumTracks))

        tracks = albumTracks

        if (album.isMultiDisc()) {
            album.tracks
                .groupBy { it.discNumber }
                .toSortedMap()
                .forEach { (disc, tracks) ->
                    albumModel.add(AlbumModel.DiscSeparator(disc))
                    tracks
                        .sortedBy { it.trackNumber }
                        .forEach { track -> albumModel.add(track.toAlbumModelTrack()) }
                }
        } else {
            album.tracks
                .sortedBy { it.trackNumber }
                .forEach { track ->
                    albumModel.add(track.toAlbumModelTrack())
                }
        }
        _albums.value = Resource.Success(albumModel.toList())
    }

}