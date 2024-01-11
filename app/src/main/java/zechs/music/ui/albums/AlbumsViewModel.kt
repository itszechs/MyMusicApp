package zechs.music.ui.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zechs.music.data.model.AlbumsResponse
import zechs.music.data.repository.MusicRepository
import zechs.music.utils.state.Resource
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {

    private val _albums = MutableStateFlow<Resource<List<AlbumsResponse>>>(Resource.Loading())
    val albums = _albums.asStateFlow()

    var hasLoaded = false
        private set

    var page = 1
        private set

    var isLastPage = false
        private set

    var isLoading = false
        private set

    private var albumsResponse = mutableListOf<AlbumsResponse>()

    init {
        fetchAlbums()
    }

    fun fetchAlbums() = viewModelScope.launch(Dispatchers.IO) {
        if (!hasLoaded) page = 1
        isLoading = true
        val response = musicRepository.getAlbums(page)
        if (response is Resource.Success) {
            hasLoaded = true
            val data = response.data!!
            albumsResponse.addAll(data.results)
            _albums.value = Resource.Success(albumsResponse)
            isLastPage = data.isLastPage()
            if (!isLastPage) page++
        } else {
            _albums.value = Resource.Error(response.message!!)
        }
        isLoading = false
    }


}