package zechs.music.ui.songs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zechs.music.data.model.SongsResponse
import zechs.music.data.repository.MusicRepository
import zechs.music.utils.state.Resource
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {

    private val _songs = MutableStateFlow<Resource<List<SongsResponse>>>(Resource.Loading())
    val songs = _songs.asStateFlow()

    var hasLoaded = false
        private set

    var page = 1
        private set

    var isLastPage = false
        private set

    var isLoading = false
        private set

    var songsResponse = mutableListOf<SongsResponse>()
        private set

    init {
        fetchSongs()
    }

    fun fetchSongs() = viewModelScope.launch(Dispatchers.IO) {
        isLoading = true
        if (!hasLoaded) page = 1
        val response = musicRepository.getSongs(page)
        if (response is Resource.Success) {
            hasLoaded = true
            val data = response.data!!
            songsResponse.addAll(data.results)
            _songs.value = Resource.Success(songsResponse.distinctBy { it._id }.toList())

            isLastPage = data.isLastPage()
            if (!isLastPage) page++
        } else {
            _songs.value = Resource.Error(response.message!!)
        }
        isLoading = false
    }


}