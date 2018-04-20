package de.heilsen.ganzhornfest;

import android.app.Application;

import de.heilsen.ganzhornfest.di.ServiceLocator;
import de.heilsen.ganzhornfest.interactor.ThreadedGetClubList;
import de.heilsen.ganzhornfest.interactor.ThreadedShowClubDetail;


public class GanzhornfestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLocator.load(new ServiceLocator(
                //new GetClubList(new FakeClubRepository())));
                new ThreadedGetClubList(new FakeClubRepository()), new ThreadedShowClubDetail(null)));

    }
}
