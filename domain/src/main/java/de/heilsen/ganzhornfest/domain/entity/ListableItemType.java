package de.heilsen.ganzhornfest.domain.entity;

public enum ListableItemType {
    CLUB(Club.class, "Verein"), FOOD(Food.class, "Essen"), DRINK(Drink.class, "Trinken"), SOME_OTHER_OFFER(SomeOtherOffer.class, "Angebote");

    private final Class itemClass; //TODO: generify classes
    private final String itemName;

    ListableItemType(Class aClass, String itemName) {
        this.itemClass = aClass;
        this.itemName = itemName;
    }

    public Class getItemClass() {
        return itemClass;
    }

    public String getItemName() {
        return itemName;
    }
}
