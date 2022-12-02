package com.nextgen.newsapp.util


import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateFormatter{
    fun formatDate(currentDate: String, targetTimeZone: String): String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val instant = Instant.parse(currentDate)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy").withZone(ZoneId.of(targetTimeZone))
            formatter.format(instant)
        } else {
            val formatter = SimpleDateFormat("dd MMM yyyy")
            formatter.timeZone = TimeZone.getTimeZone(targetTimeZone)
            formatter.format(currentDate)

        }
    }
}