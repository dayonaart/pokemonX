package id.dayona.pokemonx.data.waifu

import com.google.gson.annotations.SerializedName

data class Waifus(

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null
)