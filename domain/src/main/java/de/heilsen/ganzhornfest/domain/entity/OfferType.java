package de.heilsen.ganzhornfest.domain.entity;

public enum OfferType {
    FOOD(Food.class, "Essen"), DRINK(Drink.class, "Trinken"), ACTIONABLE_OFFER(ActionableOffer.class, "Angebote"), UNKNOWN(Object.class, "");

    private final Class itemClass; //TODO: generify classes
    private final String itemName;

    OfferType(Class aClass, String itemName) {
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
