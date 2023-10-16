package id.dayona.pokemonx.repoimpl

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.gson.Gson
import id.dayona.pokemonx.BuildConfig
import id.dayona.pokemonx.network.PokemonNetwork
import id.dayona.pokemonx.network.WaifuNetwork
import id.dayona.pokemonx.repo.AppRepo
import id.dayona.pokemonx.utils.Utils
import id.dayona.pokemonx.worker.PokemonWorker
import okio.IOException
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AppRepoImpl @Inject constructor(
    private val context: Context,
    private val waifuNetwork: WaifuNetwork,
    private val pokemonNetwork: PokemonNetwork,
) : AppRepo {
    override fun getContext(): Context = context
    override suspend fun getWaifus(): ListenableWorker.Result {
        return try {
            val res = waifuNetwork.getWaifu()
            return if (res.isSuccessful) {
                val data = Gson().toJson(res.body())
                ListenableWorker.Result.success(workDataOf("data" to Utils.compress(data)))
            } else {
                ListenableWorker.Result.retry()
            }
        } catch (e: IOException) {
            ListenableWorker.Result.failure(workDataOf("IOException" to "${e.message}"))
        } catch (e: HttpException) {
            ListenableWorker.Result.failure(workDataOf("HttpException" to "${e.message}"))
        } catch (e: UnknownHostException) {
            ListenableWorker.Result.retry()
        } catch (e: Exception) {
            ListenableWorker.Result.failure(workDataOf("Exception" to "${e.message}"))
        }
    }

    override suspend fun getPokemonList(limit: Int): ListenableWorker.Result {
        return try {
            val res = pokemonNetwork.getPokemonList("$limit")
            return if (res.isSuccessful) {
                val data = Gson().toJson(res.body())
                ListenableWorker.Result.success(workDataOf("data" to Utils.compress(data)))
            } else {
                ListenableWorker.Result.retry()
            }
        } catch (e: IOException) {
            ListenableWorker.Result.retry()
        } catch (e: HttpException) {
            ListenableWorker.Result.failure(workDataOf("HttpException" to "${e.message}"))
        } catch (e: UnknownHostException) {
            ListenableWorker.Result.retry()
        } catch (e: Exception) {
            ListenableWorker.Result.failure(workDataOf("Exception" to "${e.message}"))
        }
    }


    override suspend fun getPokeDetail(url: String): ListenableWorker.Result {
        return try {
            val res = pokemonNetwork.getPokeDetail(url.replace(BuildConfig.POKEMON_BASE_URL, ""))
            return if (res.isSuccessful) {
                val data = Gson().toJson(res.body())
                ListenableWorker.Result.success(workDataOf("data" to Utils.compress(data)))
            } else {
                ListenableWorker.Result.retry()
            }
        } catch (e: IOException) {
            ListenableWorker.Result.failure(workDataOf("failure" to "${e.message}"))
        } catch (e: HttpException) {
            ListenableWorker.Result.failure(workDataOf("failure" to "${e.message}"))
        } catch (e: UnknownHostException) {
            ListenableWorker.Result.failure(workDataOf("failure" to "${e.message}"))
        } catch (e: Exception) {
            ListenableWorker.Result.failure(workDataOf("failure" to "${e.message}"))
        }
    }

    override fun getWorker(): WorkManager = WorkManager.getInstance(context)

    override fun getWorkerRequest(param: Data): OneTimeWorkRequest.Builder {
        return OneTimeWorkRequestBuilder<PokemonWorker>().setInputData(param)
            .setConstraints(Constraints(requiresStorageNotLow = true))
            .setInitialDelay(2, TimeUnit.SECONDS)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
    }

    override fun getExpeditedWorkerRequest(param: Data): OneTimeWorkRequest.Builder {
        return OneTimeWorkRequestBuilder<PokemonWorker>().setInputData(param)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
    }
}