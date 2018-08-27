package de.heilsen.ganzhornfest.domain.entity

class Event @JvmOverloads constructor(description: String, val startDate: Long, location: String = ""): PointInTime(startDate, description, location) {
    val endDate: Long? = null
}