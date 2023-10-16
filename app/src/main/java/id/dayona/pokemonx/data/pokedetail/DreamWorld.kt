package id.dayona.pokemonx.data.pokedetail

import com.google.gson.annotations.SerializedName

data class DreamWorld(

	@field:SerializedName("front_default")
	val frontDefault: String? = null,

	@field:SerializedName("front_female")
	val frontFemale: Any? = null
)