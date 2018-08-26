package de.heilsen.ganzhornfest.data.objectbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class BusDepartureEntity @JvmOverloads constructor(val startDate: Long = 0) {
    @Id
    var id: Long = 0

    lateinit var busline: ToOne<BuslineEntity>
}

