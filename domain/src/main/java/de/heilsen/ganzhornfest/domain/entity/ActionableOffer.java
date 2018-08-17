package de.heilsen.ganzhornfest.domain.entity;

public class ActionableOffer extends Offer {

    public ActionableOffer(String name, String description) {
        super(name, description);
    }

    public ActionableOffer(String name) {
        this(name, "");
    }

    @Override
    public String toString() {
        return "ActionableOffer{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
