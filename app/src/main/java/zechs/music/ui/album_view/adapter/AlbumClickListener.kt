package zechs.music.ui.album_view.adapter

interface AlbumClickListener {

    fun onClickTrack(track: AlbumModel.Track)
    fun onClickPlay(tracks: List<AlbumModel.Track>)
    fun onClickShuffle(tracks: List<AlbumModel.Track>)
}