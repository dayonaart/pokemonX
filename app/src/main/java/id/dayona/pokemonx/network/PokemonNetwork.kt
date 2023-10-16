package id.dayona.pokemonx.network

import id.dayona.pokemonx.data.pokedetail.PokeDetail
import id.dayona.pokemonx.data.pokemon.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonNetwork {
    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: String,
        @Query("offset") offset: String = "0"
    ): Response<Pokemon>

    @GET("{url}")
    suspend fun getPokeDetail(@Path("url") url: String): Response<PokeDetail>

}