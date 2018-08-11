package de.heilsen.ganzhornfest.domain.entity;

public enum DateRestriction {
    UNKNOWN(0), SATURDAY(1), SUNDAY(2), MONDAY(3);

    final int id;

    DateRestriction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
