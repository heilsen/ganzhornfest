package de.heilsen.ganzhornfest.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RoomClubEntity(
        @PrimaryKey var id: Int = 0,
        var name: String? = null,
        var description: String? = null
)