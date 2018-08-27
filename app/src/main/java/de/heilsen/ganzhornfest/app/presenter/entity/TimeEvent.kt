package de.heilsen.ganzhornfest.app.presenter.entity

class TimeEvent(val time: String, val description: String? = null, val location: String? = null) {

    fun hasLocation(): Boolean {
        return !location.isNullOrEmpty()
    }

    fun hasDescription(): Boolean {
        return !description.isNullOrEmpty()
    }

}