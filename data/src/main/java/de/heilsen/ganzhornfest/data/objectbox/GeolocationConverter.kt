package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.GeoLocation

object GeolocationConverter {
    fun from(geoLocationEntityList: List<GeoLocationEntity>): List<GeoLocation> {
        return geoLocationEntityList.map { geoLocationEntity: GeoLocationEntity -> from(geoLocationEntity) }
    }

    fun from(geoLocationEntity: GeoLocationEntity): GeoLocation {
        return GeoLocation(geoLocationEntity.lat, geoLocationEntity.lng)
    }
}
