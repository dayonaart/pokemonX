package id.dayona.pokemonx.data.waifu

import com.google.gson.annotations.SerializedName

data class ImagesItem(

    @field:SerializedName("favorites")
	val favorites: Int? = null,

    @field:SerializedName("extension")
	val extension: String? = null,

    @field:SerializedName("liked_at")
	val likedAt: Any? = null,

    @field:SerializedName("signature")
	val signature: String? = null,

    @field:SerializedName("artist")
	val artist: Any? = null,

    @field:SerializedName("byte_size")
	val byteSize: Int? = null,

    @field:SerializedName("source")
	val source: Any? = null,

    @field:SerializedName("url")
	val url: String? = null,

    @field:SerializedName("tags")
	val tags: List<TagsItem?>? = null,

    @field:SerializedName("is_nsfw")
	val isNsfw: Boolean? = null,

    @field:SerializedName("dominant_color")
	val dominantColor: String? = null,

    @field:SerializedName("uploaded_at")
	val uploadedAt: String? = null,

    @field:SerializedName("preview_url")
	val previewUrl: String? = null,

    @field:SerializedName("width")
	val width: Int? = null,

    @field:SerializedName("image_id")
	val imageId: Int? = null,

    @field:SerializedName("height")
	val height: Int? = null
)