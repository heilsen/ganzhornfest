package de.heilsen.ganzhornfest.data.objectbox

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class DrinkEntity @JvmOverloads constructor(val name: String = "", val description: String = "") {

    @Id
    var id: Long = 0

    @Backlink
    lateinit var clubs: ToMany<ClubEntity>
}
