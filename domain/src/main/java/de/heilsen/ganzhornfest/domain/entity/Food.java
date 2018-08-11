package de.heilsen.ganzhornfest.domain.entity;

public class Food extends ListableItem {
    public Food(String name, String description) {
        super(name, description);
    }

    public Food(String name) {
        this(name, "");
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
