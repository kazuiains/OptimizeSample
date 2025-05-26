package id.adiyusuf.optimizesample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.adiyusuf.optimizesample.screen.sharing.singleton.CountRepository
import id.adiyusuf.optimizesample.screen.sharing.singleton.CountRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindCountRepository(
        countRepositoryImpl: CountRepositoryImpl
    ): CountRepository
}