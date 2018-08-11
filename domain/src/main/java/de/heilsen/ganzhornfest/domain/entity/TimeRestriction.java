package de.heilsen.ganzhornfest.domain.entity;

public enum TimeRestriction {
    LUNCHTIME(1), UNKNOWN(0);

    final int id;

    TimeRestriction(int id) {
        this.id = id;
    }
}
