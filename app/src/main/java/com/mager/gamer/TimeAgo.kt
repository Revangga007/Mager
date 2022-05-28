package com.mager.gamer

import java.util.*

object TimeAgo {

    private const val SECOND = 1
    private const val MINUTE = 60 * SECOND
    private const val HOUR = 60 * MINUTE
    private const val DAY = 24 * HOUR
    private const val MONTH = 30 * DAY
    private const val YEAR = 12 * MONTH

    private fun currentDate(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }

    // Long: time in millisecond
    fun Date.toTimeAgo(): String {
        val time = this.time
        val now = currentDate()

        // convert back to second
        val diff = (now - time) / 1000

        return when {
            diff < MINUTE -> "just now"
            diff < 2 * MINUTE -> "1 menit lalu"
            diff < 60 * MINUTE -> "${diff / MINUTE} menit lalu"
            diff < 2 * HOUR -> "1 jam lalu"
            diff < 24 * HOUR -> "${diff / HOUR} jam lalu"
            diff < 2 * DAY -> "kemarin"
            diff < 30 * DAY -> "${diff / DAY} hari lalu"
            diff < 2 * MONTH -> "1 bulan lalu"
            diff < 12 * MONTH -> "${diff / MONTH} bulan lalu"
            diff < 2 * YEAR -> "1 tahun lalu"
            else -> "${diff / YEAR} tahun lalu"
        }
    }
}