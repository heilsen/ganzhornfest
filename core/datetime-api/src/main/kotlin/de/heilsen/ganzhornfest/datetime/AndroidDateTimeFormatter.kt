package de.heilsen.ganzhornfest.datetime

import android.content.Context
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.text.DateFormat
import java.time.ZoneId
import java.util.*

object AndroidDateTimeFormatter {
    /**
     * Formats given [LocalDate] according to Context (i.e. Android System Settings). The format is
     * otherwise only affected by its [style].
     */
    fun localizedDateFormat(context: Context, style: Int, localDateString: String): String =
        localizedDateFormat(context, style, LocalDate.parse(localDateString))

    /**
     * Formats given [LocalDate] according to Context (i.e. Android System Settings). The format is
     * otherwise only affected by its [style].
     */
    fun localizedDateFormat(context: Context, style: Int, localDate: LocalDate): String {
        val locale = context.resources.configuration.locales[0]
        val dateTimeFormatLong = DateFormat.getDateInstance(style, locale);
        val java8Date = localDate.toJavaLocalDate()
        val java7Date: Date =
            Date.from(
                java8Date
                    .atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
            )
        return dateTimeFormatLong.format(java7Date)
    }

}