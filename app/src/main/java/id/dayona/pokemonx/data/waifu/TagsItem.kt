package id.dayona.pokemonx.data.waifu

import com.google.gson.annotations.SerializedName

data class TagsItem(

	@field:SerializedName("is_nsfw")
	val isNsfw: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("tag_id")
	val tagId: Int? = null,

	@field:SerializedName("description")
	val description: String? = null
)