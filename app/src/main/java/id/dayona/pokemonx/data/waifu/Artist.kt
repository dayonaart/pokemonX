package id.dayona.pokemonx.data.waifu

import com.google.gson.annotations.SerializedName

data class Artist(

	@field:SerializedName("deviant_art")
	val deviantArt: Any? = null,

	@field:SerializedName("twitter")
	val twitter: String? = null,

	@field:SerializedName("pixiv")
	val pixiv: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("artist_id")
	val artistId: Int? = null,

	@field:SerializedName("patreon")
	val patreon: Any? = null
)