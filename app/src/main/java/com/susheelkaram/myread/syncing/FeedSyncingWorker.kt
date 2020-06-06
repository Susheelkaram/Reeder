package com.susheelkaram.myread.syncing

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.trackky.utils.Notify

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class FeedSyncingWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    var notify = Notify(context)

    companion object {
        const val FEED_SYNC_WORK_NOTIFICATION_ID = 101

        fun sendNewArticleNotification(context: Context, articles: List<FeedArticle>) {
            var firstArticle = articles[0]
            var title = "My Feed - ${articles.size} new article"
            var description = firstArticle.title

            if (articles.size > 1) {
                title += "s"
                description = firstArticle.title + " & ${articles.size - 1} new articles"
            }

            val openAppIntent: Intent? =
                context.packageManager.getLaunchIntentForPackage("com.susheelkaram.myread")
            openAppIntent?.let {
                val pendingIntent: PendingIntent =
                    PendingIntent.getActivity(context, 0, openAppIntent, 0)
                Notify(context).showSimpleNotificationWithIntent(
                    title,
                    description,
                    pendingIntent
                )
            }
        }
    }

    override suspend fun doWork(): Result {
        var foregroundNotification =
            notify.createSimpleNotificationBuilder("Syncing your feed", "")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
        setForeground(ForegroundInfo(FEED_SYNC_WORK_NOTIFICATION_ID, foregroundNotification))

        var feedSync = FeedSync(context)
        var newArticles = feedSync.sync()

        if (newArticles.isNotEmpty()) sendNewArticleNotification(newArticles)

        with(NotificationManagerCompat.from(context)) {
            cancel(FEED_SYNC_WORK_NOTIFICATION_ID)
        }

        return Result.success()
    }

    private fun sendNewArticleNotification(articles: List<FeedArticle>) {
        var firstArticle = articles[0]
        var title = "My Feed - ${articles.size} new articles"
        var description = firstArticle.title

        if (articles.size > 1) {
            description = firstArticle.title + " & ${articles.size - 1} new articles"
        }

        val openAppIntent: Intent? =
            context.packageManager.getLaunchIntentForPackage("com.susheelkaram.myread")
        openAppIntent?.let {
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 0, openAppIntent, 0)
            notify.showSimpleNotificationWithIntent(
                title,
                description,
                pendingIntent
            )
        }
    }

}