package de.heilsen.ganzhornfest.domain.interactor;

import de.heilsen.ganzhornfest.domain.entity.Area;
import de.heilsen.ganzhornfest.domain.entity.Poi;

public class DrawMapInteractor {

    private Poi ganzhornfestAreaNorthEast = new Poi(49.191880, 9.225486);
    private Poi ganzhornfestAreaSouthWest = new Poi(49.190600, 9.221356);

    private Area ganzhornfestArea = new Area(ganzhornfestAreaNorthEast, ganzhornfestAreaSouthWest);


}
