package id.dayona.pokemonx.viewmodel

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import id.dayona.pokemonx.R
import id.dayona.pokemonx.repo.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(repo: Lazy<AppRepo>) : ViewModel() {
    private val instance = repo.get()
    private var mediaPlayer: MediaPlayer? = null
    fun pokemonCries(id: Int?) = viewModelScope.launch(Dispatchers.Default) {
        val field = R.raw::class.java.getDeclaredField("pokemon$id")
        field.isAccessible = true
        val cries = field.get(field)
        mediaPlayer = MediaPlayer.create(instance.getContext(), cries as Int)
        mediaPlayer?.start()
    }
}