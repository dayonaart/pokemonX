package id.dayona.pokemonx.di

sealed interface CoreHelper<out T>
class CoreSuccess<T>(val data: T) : CoreHelper<T>
class CoreError<T : Any>(val code: String, val message: String?) : CoreHelper<T>
class CoreException<T : Any>(val e: String) : CoreHelper<T>
object CoreLoading : CoreHelper<Nothing>