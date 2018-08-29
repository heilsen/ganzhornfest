package de.heilsen.ganzhornfest.data.objectbox

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class GeoLocationEntity(val lat: Double = 0.0, val lng: Double = 0.0) {
    @Id
    var id: Long = 0

    @Backlink
    lateinit var club: ToOne<ClubEntity>
}
