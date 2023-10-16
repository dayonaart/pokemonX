package id.dayona.pokemonx.data.pokedetail

import com.google.gson.annotations.SerializedName

data class VersionDetailsItem(

	@field:SerializedName("version")
	val version: Version? = null,

	@field:SerializedName("rarity")
	val rarity: Int? = null
)