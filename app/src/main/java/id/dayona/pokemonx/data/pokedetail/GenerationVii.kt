package id.dayona.pokemonx.data.pokedetail

import com.google.gson.annotations.SerializedName

data class GenerationVii(

	@field:SerializedName("icons")
	val icons: Icons? = null,

	@field:SerializedName("ultra-sun-ultra-moon")
	val ultraSunUltraMoon: UltraSunUltraMoon? = null
)