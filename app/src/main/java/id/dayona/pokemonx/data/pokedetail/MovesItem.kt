package id.dayona.pokemonx.data.pokedetail

import com.google.gson.annotations.SerializedName

data class MovesItem(

	@field:SerializedName("version_group_details")
	val versionGroupDetails: List<VersionGroupDetailsItem?>? = null,

	@field:SerializedName("move")
	val move: Move? = null
)