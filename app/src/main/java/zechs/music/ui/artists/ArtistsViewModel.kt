package zechs.music.ui.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zechs.music.data.model.ArtistsResponse
import zechs.music.data.repository.MusicRepository
import zechs.music.utils.state.Resource
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {

    private val _artists = MutableStateFlow<Resource<List<ArtistsResponse>>>(Resource.Loading())
    val artists = _artists.asStateFlow()

    var hasLoaded = false
        private set

    init {
        fetchArtists()
    }

    private fun fetchArtists() = viewModelScope.launch {
        val response = musicRepository.getArtists()
        if (response is Resource.Success) {
            hasLoaded = true
        }
        _artists.value = response
    }


}