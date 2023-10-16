package id.dayona.pokemonx.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.workDataOf
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import id.dayona.pokemonx.data.pokedetail.PokeDetail
import id.dayona.pokemonx.data.pokemon.Pokemon
import id.dayona.pokemonx.repo.AppRepo
import id.dayona.pokemonx.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(repo: Lazy<AppRepo>) : ViewModel() {
    private val TAG = "PokemonViewModel"
    private val instance = repo.get()

    /* WORK REQUEST */
    private val notificationReq =
        instance.getExpeditedWorkerRequest(workDataOf("param" to "show_notification")).build()

    private val pokemonUrlListReq =
        instance.getWorkerRequest(workDataOf("param" to "pokemon", "limit" to 10)).build()

    private fun pokeDetailReq(url: String): OneTimeWorkRequest {
        return instance.getWorkerRequest(
            workDataOf(
                "param" to "pokemon_detail",
                "url" to url
            )
        ).build()
    }
    //WORK REQUEST

    val pokemonListUrlState =
        instance.getWorker().getWorkInfoByIdLiveData(pokemonUrlListReq.id).asFlow()

    private var pokemonListUrl by mutableStateOf<List<String?>>(listOf())
    var pokeDetail by mutableStateOf<List<PokeDetail?>>(listOf())
    private fun pokeDetailState(id: UUID): Flow<WorkInfo> {
        return instance.getWorker().getWorkInfoByIdLiveData(id).asFlow()

    }

    init {
        viewModelScope.launch {
//            getPokemon()
        }
    }

    private suspend fun getPokemon() = viewModelScope.launch(Dispatchers.IO) {
        instance.getWorker().enqueue(pokemonUrlListReq)
        pokemonListUrlState.collectLatest {
            when (it.state.isFinished) {
                true -> {
                    pokemonListUrl =
                        Utils.decompress<Pokemon>(it?.outputData?.getString("data"))?.results?.map { p -> p?.url }
                            ?: listOf()
                    repeat(pokemonListUrl.size) { i ->
                        getPokeDetail(pokemonListUrl[i] ?: "")
                        Log.d(TAG, "getPokemon: $i")
                        delay(200)
                    }
                }

                false -> {}
            }
        }
    }

    private suspend fun getPokeDetail(url: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val req = pokeDetailReq(url)
            instance.getWorker().enqueue(req)
            pokeDetailState(req.id).collectLatest {
                when (it.state.isFinished) {
                    true -> {
                        val res = Utils.decompress<PokeDetail>(it.outputData.getString("data"))
                        pokeDetail += res
                    }

                    false -> {}
                }
            }
        }

}