package de.heilsen.ganzhornfest.domain.interactor;

import de.heilsen.ganzhornfest.domain.entity.Area;
import de.heilsen.ganzhornfest.domain.entity.GeoLocation;

public class DrawMapInteractor {

    private GeoLocation ganzhornfestAreaNorthEast = new GeoLocation(49.191880, 9.225486);
    private GeoLocation ganzhornfestAreaSouthWest = new GeoLocation(49.190600, 9.221356);

    private Area ganzhornfestArea = new Area(ganzhornfestAreaNorthEast, ganzhornfestAreaSouthWest);


}
