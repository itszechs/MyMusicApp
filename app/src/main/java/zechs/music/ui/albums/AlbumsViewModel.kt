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

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() = viewModelScope.launch(Dispatchers.IO) {
        val response = musicRepository.getAlbums(page = 1)
        if (response is Resource.Success) {
            _albums.value = Resource.Success(response.data!!.results)
        } else {
            _albums.value = Resource.Error(response.message!!)
        }
    }

}