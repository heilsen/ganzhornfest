package de.heilsen.ganzhornfest.core

interface CrashReporter {
    fun recordNonFatal(
        throwable: Throwable,
        tag: String,
    )

    fun setCustomKey(
        key: String,
        value: String,
    )
}
