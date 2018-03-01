package de.heilsen.ganzhornfest;

import android.app.Application;

import de.heilsen.ganzhornfest.interactor.ThreadedGetClubList;


public class GanzhornfestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLocator.load(new ServiceLocator(
                new ThreadedGetClubList(new FakeClubRepository())));

    }
}
