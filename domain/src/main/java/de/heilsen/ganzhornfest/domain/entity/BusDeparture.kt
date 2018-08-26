package de.heilsen.ganzhornfest.domain.entity

class BusDeparture constructor(val busline: Busline, val date: Long) : PointInTime(date, busline.description)

data class Busline(val destination: String, val description: String = "")
