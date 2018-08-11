package de.heilsen.ganzhornfest.domain.entity;

import java.util.EnumSet;

public class FoodOfferImpl implements FoodOffer {
    private Food food;
    private boolean limitedOffer;
    private EnumSet<DateRestriction> dateRestrictions;
    private EnumSet<TimeRestriction> timeRestrictions;

    @Override
    public Food getFood() {
        return food;
    }

    @Override
    public boolean isLimitedOffer() {
        return limitedOffer;
    }

    @Override
    public EnumSet<DateRestriction> getDateRestrictions() {
        return dateRestrictions;
    }

    @Override
    public EnumSet<TimeRestriction> getTimeRestrictions() {
        return timeRestrictions;
    }
}
