package id.dayona.pokemonx.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.dayona.pokemonx.repo.AppRepo
import id.dayona.pokemonx.repoimpl.AppRepoImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRepo {
    @Binds
    abstract fun bindRepo(appRepoImpl: AppRepoImpl): AppRepo
}