package zechs.music.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zechs.music.data.remote.MusicApi
import zechs.music.data.repository.MusicRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMusicRepository(
        musicApi: MusicApi
    ): MusicRepository {
        return MusicRepository(musicApi)
    }

}