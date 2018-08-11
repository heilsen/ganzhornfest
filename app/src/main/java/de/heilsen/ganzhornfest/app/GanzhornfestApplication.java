package de.heilsen.ganzhornfest.app;

import android.app.Application;

import de.heilsen.ganzhornfest.app.data.MyObjectBox;
import de.heilsen.ganzhornfest.app.data.ObjectBoxClubRepository;
import de.heilsen.ganzhornfest.app.data.ObjectBoxDrinkRepository;
import de.heilsen.ganzhornfest.app.data.ObjectBoxFoodRepository;
import de.heilsen.ganzhornfest.app.di.ServiceLocator;
import de.heilsen.ganzhornfest.app.interactor.ThreadedGetClubInfoInteractor;
import de.heilsen.ganzhornfest.app.interactor.ThreadedGetListInteractor;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.repository.Repository;
import de.heilsen.ganzhornfest.domain.repository.RepositoryProvider;
import io.objectbox.BoxStore;


public class GanzhornfestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BoxStore boxStore = MyObjectBox.builder().androidContext(this).build();
        RepositoryProvider repositoryProvider = new RepositoryProvider();
        ObjectBoxDrinkRepository objectBoxDrinkRepository = new ObjectBoxDrinkRepository(boxStore);
        Repository<Drink> drinkRepository = objectBoxDrinkRepository;
        ObjectBoxFoodRepository objectBoxFoodRepository = new ObjectBoxFoodRepository(boxStore);
        Repository<Food> foodRepository = objectBoxFoodRepository;
        Repository<Club> objectBoxClubRepository = new ObjectBoxClubRepository(boxStore, objectBoxFoodRepository, objectBoxDrinkRepository);
        repositoryProvider.addRepository(Club.class, objectBoxClubRepository);
        repositoryProvider.addRepository(Food.class, foodRepository);

        ServiceLocator.load(new ServiceLocator(new ThreadedGetListInteractor(repositoryProvider), new ThreadedGetClubInfoInteractor(objectBoxClubRepository)));

    }
}
