package de.heilsen.ganzhornfest.domain.interactor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import de.heilsen.ganzhornfest.domain.entity.OfferType;
import de.heilsen.ganzhornfest.domain.repository.Repository;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class OfferListInteractorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private OfferListInteractor offerListInteractor;
    @Mock
    private Repository<Food> foodRepository;
    @Mock
    private Repository<Drink> drinkRepository;
    @Mock
    private Repository<ActionableOffer> actionableOfferRepository;
    @Mock
    private OfferListInteractor.Callback callback;
    @Mock
    private OfferListInteractor.DetailCallback detailCallback;
    private Food food;
    private Drink drink;
    private ActionableOffer actionableOffer;

    @Before
    public void setUp() throws Exception {
        food = new Food("foo", "bar");
        drink = new Drink("bar", "baz");
        actionableOffer = new ActionableOffer("some", "thing");
        doReturn(singletonList(food)).when(foodRepository).getAll();
        doReturn(singletonList(drink)).when(drinkRepository).getAll();
        doReturn(singletonList(actionableOffer)).when(actionableOfferRepository).getAll();
        doReturn(food).when(foodRepository).get(anyString());
        doReturn(drink).when(drinkRepository).get(anyString());
        doReturn(actionableOffer).when(actionableOfferRepository).get(anyString());

        offerListInteractor = new OfferListInteractor(foodRepository, drinkRepository, actionableOfferRepository);
    }

    @Test
    public void listOffer_food() {
        offerListInteractor.listOffer(OfferType.FOOD, callback);

        verify(callback).showOfferList(new ArrayList<>(foodRepository.getAll()));
    }

    @Test
    public void listOffer_drink() {
        offerListInteractor.listOffer(OfferType.DRINK, callback);

        verify(callback).showOfferList(new ArrayList<>(drinkRepository.getAll()));
    }

    @Test
    public void listOffer_actionableOffer() {
        offerListInteractor.listOffer(OfferType.ACTIONABLE_OFFER, callback);

        verify(callback).showOfferList(new ArrayList<>(actionableOfferRepository.getAll()));
    }

    @Test
    public void listOffer_unknown() {
        offerListInteractor.listOffer(OfferType.UNKNOWN, callback);

        verify(callback).showOfferList(emptyList());
    }


    @Test
    public void selectOffer_food() {
        offerListInteractor.selectOffer(OfferType.FOOD, "foo", detailCallback);

        verify(detailCallback).showOfferDetail(food);
    }

    @Test
    public void selectOffer_drink() {
        offerListInteractor.selectOffer(OfferType.DRINK, "bar", detailCallback);

        verify(detailCallback).showOfferDetail(drink);
    }

    @Test
    public void selectOffer_actionableOffer() {
        offerListInteractor.selectOffer(OfferType.ACTIONABLE_OFFER, "some", detailCallback);

        verify(detailCallback).showOfferDetail(actionableOffer);
    }

    @Test
    public void selectOffer_unknown() {
        offerListInteractor.selectOffer(OfferType.UNKNOWN, "", detailCallback);

        verify(detailCallback).showOfferDetail(new Offer("", ""));
    }

}