package id.dayona.pokemonx

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.hilt.android.HiltAndroidApp
import id.dayona.pokemonx.repo.AppRepo
import id.dayona.pokemonx.worker.PokemonWorker
import javax.inject.Inject

@HiltAndroidApp
class PokemonXApp : Application(), Configuration.Provider {
    @Inject
    lateinit var customWorkerFactory: CustomWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(customWorkerFactory).build()
    }
}

class CustomWorkerFactory @Inject constructor(private val appRepo: AppRepo) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return PokemonWorker(appRepo, appContext, workerParameters)
    }
}

