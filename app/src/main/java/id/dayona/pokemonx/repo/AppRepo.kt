package id.dayona.pokemonx.repo

import android.content.Context
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

interface AppRepo {
    fun getContext(): Context
    fun getWorker(): WorkManager
    fun getWorkerRequest(param: Data): OneTimeWorkRequest.Builder
    fun getExpeditedWorkerRequest(param: Data): OneTimeWorkRequest.Builder
    suspend fun getWaifus(): ListenableWorker.Result
    suspend fun getPokemonList(limit: Int): ListenableWorker.Result
    suspend fun getPokeDetail(url: String): ListenableWorker.Result

}