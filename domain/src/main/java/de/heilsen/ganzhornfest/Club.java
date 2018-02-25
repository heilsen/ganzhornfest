package de.heilsen.ganzhornfest;


public class Club implements DisplayableItem {
    private String name;
    private String description;

    public Club(String name) {
        this(name, "");
    }

    public Club(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Club{" +
               "name='" + name + '\'' +
               ", description='" + description + '\'' +
               '}';
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
