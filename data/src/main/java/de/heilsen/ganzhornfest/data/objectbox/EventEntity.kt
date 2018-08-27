package de.heilsen.ganzhornfest.data.objectbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class EventEntity @JvmOverloads constructor(val description: String = "", val startDate: Long = 0, val location: String = "") {
    @Id
    var id: Long = 0
}

