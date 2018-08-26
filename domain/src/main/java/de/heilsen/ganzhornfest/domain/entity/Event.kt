package de.heilsen.ganzhornfest.domain.entity

class Event(description: String, val startDate: Long): PointInTime(startDate, description) {
    val endDate: Long? = null
}