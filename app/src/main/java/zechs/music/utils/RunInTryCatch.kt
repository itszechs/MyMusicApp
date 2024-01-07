package zechs.music.utils

internal inline fun <reified T> runInTryCatch(
    tryBlock: () -> T,
    catchBlock: (Exception) -> T
): T {
    return try {
        tryBlock()
    } catch (e: Exception) {
        catchBlock(e)
    }
}