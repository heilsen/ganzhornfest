package de.heilsen.ganzhornfest.domain.entity;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Club extends ListableItem {
    private String abbreviation;
    private List<Food> foodList;
    private List<Drink> drinkList;
    private List<SomeOtherOffer> someOtherOfferList;

    public Club(String name, String description, String abbreviation, List<Food> foodList, List<Drink> drinkList, List<SomeOtherOffer> someOtherOfferList) {
        super(name, description);
        this.abbreviation = abbreviation;
        this.foodList = foodList;
        this.drinkList = drinkList;
        this.someOtherOfferList = someOtherOfferList;
    }

    public Club(String name, String description) {
        this(name, description, "");
    }

    public Club(String name) {
        this(name, "");
    }

    public Club() {
        this("", "");
    }

    public Club(String name, String description, String abbreviation) {
        this(name, description, abbreviation, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }

    public List<SomeOtherOffer> getSomeOtherOfferList() {
        return someOtherOfferList;
    }

    @Override
    public String toString() {
        return "Club{" +
                "abbreviation='" + abbreviation + '\'' +
                ", foodList=" + foodList +
                ", drinkList=" + drinkList +
                ", someOtherOfferList=" + someOtherOfferList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Club club = (Club) o;
        return Objects.equals(abbreviation, club.abbreviation) &&
                Objects.equals(foodList, club.foodList) &&
                Objects.equals(drinkList, club.drinkList) &&
                Objects.equals(someOtherOfferList, club.someOtherOfferList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), abbreviation, foodList, drinkList, someOtherOfferList);
    }
}
