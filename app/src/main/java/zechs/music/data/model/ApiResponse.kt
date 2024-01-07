package zechs.music.data.model

data class ApiResponse<T>(
    val page: Int,
    val totalPage: Int,
    val results: List<T>
) {
    fun isLastPage() = page >= totalPage
}