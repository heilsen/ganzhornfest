package de.heilsen.ganzhornfest.domain.entity;

import java.util.EnumSet;

public interface FoodOffer {
    Food getFood();
    boolean isLimitedOffer();
    EnumSet<DateRestriction> getDateRestrictions();
    EnumSet<TimeRestriction> getTimeRestrictions();
}
