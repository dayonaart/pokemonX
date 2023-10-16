package id.dayona.pokemonx.worker

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import id.dayona.pokemonx.MainActivity
import id.dayona.pokemonx.repo.AppRepo
import kotlinx.coroutines.delay

@HiltWorker
class PokemonWorker @AssistedInject constructor(
    @Assisted private val appRepo: AppRepo,
    @Assisted context: Context,
    @Assisted private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return when (workerParameters.inputData.getString("param")) {
            "waifu" -> appRepo.getWaifus()
            "pokemon" -> {
                val limit = workerParameters.inputData.getInt("limit", 0)
                appRepo.getPokemonList(limit)
            }

            "pokemon_detail" -> {
                val url = workerParameters.inputData.getString("url")
                appRepo.getPokeDetail(url ?: "")
            }

            "show_notification" -> {
                delay(10000L)
                Result.success()
            }

            else -> Result.failure()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return foregroundInfo()
    }

    private fun foregroundInfo(): ForegroundInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(
                999,
                createNotification(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_NONE
            )
        } else {
            ForegroundInfo(999, createNotification())
        }
    }

    @SuppressLint("MissingPermission")
    private fun createNotification(): Notification {
        val channelId = "POKE_NOTIF"
        val channelName = "POKE_NOTIF_NAME"
        val intent = Intent(appRepo.getContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(appRepo.getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(appRepo.getContext(), channelId)
            .setSmallIcon(androidx.hilt.work.R.drawable.notification_bg)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "this is poke notification"
            }
            val notificationManager: NotificationManager =
                appRepo.getContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        return builder.build()
    }
}