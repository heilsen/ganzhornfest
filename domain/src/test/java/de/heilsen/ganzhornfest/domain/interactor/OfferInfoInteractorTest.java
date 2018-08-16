package de.heilsen.ganzhornfest.domain.interactor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.Action;

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import de.heilsen.ganzhornfest.domain.entity.OfferType;
import de.heilsen.ganzhornfest.domain.interactor.testdouble.FakeClubRepository;
import de.heilsen.ganzhornfest.domain.repository.Repository;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class OfferInfoInteractorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Repository<Club> clubRepository;
    @Mock
    private Repository<Food> foodRepository;
    @Mock
    private Repository<Drink> drinkRepository;
    @Mock
    private Repository<ActionableOffer> actionableOfferRepository;
    private Club stpc;
    private OfferInfoInteractor offerItemInteractor;
    private Club lionsClub;
    private Club kolping;
    private Club soccerClub;

    @Before
    public void setUp() throws Exception {
        Food food = new Food("a", "b");

        soccerClub =
                new Club("Club Soccer", "Club 1 is awesome", emptyList(), emptyList(), singletonList(new ActionableOffer("c")));
        lionsClub =
                new Club("Lions Club", "", Collections.singletonList(food), Collections.singletonList(new Drink("a")));
        stpc =
                new Club("St. Paulus Club", "Früher katholischer Jugendclub, jetzt nur noch Club", Collections.singletonList(food), Collections
                        .singletonList(new Drink("b")));
        kolping = new Club("Kolping", "", emptyList(), Arrays.asList(new Drink("a"), new Drink("b"), new Drink("c")));

        clubRepository = new FakeClubRepository(soccerClub, lionsClub, stpc, kolping);
        offerItemInteractor =
                new OfferInfoInteractor(clubRepository, foodRepository, drinkRepository, actionableOfferRepository);
    }

    @Test
    public void showInfo_forFood() {
        Food food = mock(Food.class);
        doReturn(food).when(foodRepository).get(anyString());
        offerItemInteractor.showInfo(OfferType.FOOD, "a", new OfferInfoInteractor.Callback() {
            @Override
            public void show(Offer offer, List<Club> clubList) {
                assertThat(offer, is(food));
                assertThat(clubList, contains(lionsClub, stpc));
            }
        });
    }

    @Test
    public void showInfo_forDrink() {
        Drink drink = mock(Drink.class);
        doReturn(drink).when(drinkRepository).get(anyString());
        offerItemInteractor.showInfo(OfferType.DRINK, "b", new OfferInfoInteractor.Callback() {
            @Override
            public void show(Offer offer, List<Club> clubList) {
                assertThat(offer, is(drink));
                assertThat(clubList, contains(kolping, stpc));
            }
        });
    }

    @Test
    public void showInfo_forActionableOffer() {
        ActionableOffer actionableOffer = mock(ActionableOffer.class);
        doReturn(actionableOffer).when(actionableOfferRepository).get(anyString());
        offerItemInteractor.showInfo(OfferType.ACTIONABLE_OFFER, "c", new OfferInfoInteractor.Callback() {
            @Override
            public void show(Offer offer, List<Club> clubList) {
                assertThat(offer, is(actionableOffer));
                assertThat(clubList, contains(soccerClub));
            }
        });
    }

    @Test
    public void showInfo_forUnknown() {
        offerItemInteractor.showInfo(OfferType.UNKNOWN, "a", new OfferInfoInteractor.Callback() {
            @Override
            public void show(Offer offer, List<Club> clubList) {
                assertThat(offer, is(new Offer("", "")));
                assertThat(clubList, is(emptyIterable()));
            }
        });
    }
}