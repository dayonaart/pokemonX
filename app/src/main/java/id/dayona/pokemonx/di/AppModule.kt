package id.dayona.pokemonx.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.dayona.pokemonx.BuildConfig
import id.dayona.pokemonx.network.PokemonNetwork
import id.dayona.pokemonx.network.WaifuNetwork
import id.dayona.pokemonx.repoimpl.AppRepoImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWaifuNetwork(): WaifuNetwork {
        val timeoutInSeconds = 30L
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        val http = OkHttpClient()
            .newBuilder()
            .addInterceptor(logging)
            .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder().baseUrl(BuildConfig.WAIFU_BASE_URL)
            .client(http)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
            .create(WaifuNetwork::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonNetwork(): PokemonNetwork {
        val timeoutInSeconds = 30L
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        val http = OkHttpClient()
            .newBuilder()
            .addInterceptor(logging)
            .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder().baseUrl(BuildConfig.POKEMON_BASE_URL)
            .client(http)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
            .create(PokemonNetwork::class.java)
    }

    @Provides
    @Singleton
    fun provideRepo(
        app: Application,
        waifuNetwork: WaifuNetwork,
        pokemonNetwork: PokemonNetwork
    ): AppRepoImpl {
        return AppRepoImpl(app.applicationContext, waifuNetwork, pokemonNetwork)
    }
}