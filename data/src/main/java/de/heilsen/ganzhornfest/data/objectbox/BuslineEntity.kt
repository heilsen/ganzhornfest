package de.heilsen.ganzhornfest.data.objectbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class BuslineEntity(val direction: String = "") {
    @Id
    var id: Long = 0
}
