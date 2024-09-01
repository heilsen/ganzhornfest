package de.heilsen.ganzhornfest.core.datetime

import android.annotation.SuppressLint
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import java.text.DateFormat
import java.text.SimpleDateFormat
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

fun formatToLocalDate(epochDuration: Duration, dateFormat: Int): String =
    DateFormat.getDateInstance(dateFormat).format(epochDuration.inWholeMilliseconds)

fun formatToLocalDate(instant: Instant, dateFormat: Int): String =
    formatToLocalDate(instant.toEpochMilliseconds().milliseconds, dateFormat)

fun formatToLocalDate(localDate: LocalDate, dateFormat: Int): String =
    formatToLocalDate(localDate.atStartOfDayIn(TimeZone.currentSystemDefault()), dateFormat)

fun formatToLocalDate(localDate: LocalDateTime, dateFormat: Int): String =
    formatToLocalDate(localDate.date, dateFormat)

fun formatToLocalTime(epochDuration: Duration, timeFormat: Int): String =
    DateFormat.getTimeInstance(timeFormat).format(epochDuration.inWholeMilliseconds)

fun formatToLocalTime(instant: Instant, timeFormat: Int): String =
    formatToLocalTime(instant.toEpochMilliseconds().milliseconds, timeFormat)

fun formatToLocalTime(localDateTime: LocalDateTime, timeFormat: Int): String =
    formatToLocalTime(localDateTime.toInstant(TimeZone.currentSystemDefault()), timeFormat)

fun formatToLocalDateTime(epochDuration: Duration, dateFormat: Int, timeFormat: Int): String =
    DateFormat.getDateTimeInstance(dateFormat, timeFormat).format(epochDuration.inWholeMilliseconds)

fun formatToLocalDateTime(instant: Instant, dateFormat: Int, timeFormat: Int): String =
    formatToLocalDateTime(instant.toEpochMilliseconds().milliseconds, dateFormat, timeFormat)

fun formatToLocalDateTime(localDateTime: LocalDateTime, dateFormat: Int, timeFormat: Int): String =
    formatToLocalDateTime(
        localDateTime.toInstant(TimeZone.currentSystemDefault()),
        dateFormat,
        timeFormat
    )

fun dayOfTheWeek(localDate: LocalDateTime): String =
    dayOfTheWeek(localDate.date)

fun dayOfTheWeek(localDate: LocalDate): String =
    dayOfTheWeek(localDate.atStartOfDayIn(TimeZone.currentSystemDefault()))

fun dayOfTheWeek(instant: Instant): String =
    dayOfTheWeek(instant.toEpochMilliseconds().milliseconds)

@SuppressLint("SimpleDateFormat") // Short date format does not include day of week
fun dayOfTheWeek(epochDuration: Duration): String =
    SimpleDateFormat("EEE").format(epochDuration.inWholeMilliseconds)