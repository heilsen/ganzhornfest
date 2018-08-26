package de.heilsen.ganzhornfest.app.presenter.entity

class TimeEvent(val time: String, val description: String? = null, val location: String?) {

    fun hasLocation(): Boolean {
        return location != null
    }

    fun hasDescription(): Boolean {
        return !description.isNullOrEmpty()
    }

}