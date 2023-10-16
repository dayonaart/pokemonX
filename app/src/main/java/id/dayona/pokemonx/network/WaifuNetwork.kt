package id.dayona.pokemonx.network

import id.dayona.pokemonx.data.waifu.Waifus
import retrofit2.Response
import retrofit2.http.GET

interface WaifuNetwork {
    @GET("search?included_tags=maid&height=>=2000&many=true")
    suspend fun getWaifu(): Response<Waifus?>
}

