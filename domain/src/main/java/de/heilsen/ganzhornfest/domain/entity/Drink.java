package de.heilsen.ganzhornfest.domain.entity;

public class Drink extends ListableItem {
    public Drink(String name, String description) {
        super(name, description);
    }

    public Drink(String name) {
        this(name, "");
    }

    @Override
    public String toString() {
        return "Drink{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
