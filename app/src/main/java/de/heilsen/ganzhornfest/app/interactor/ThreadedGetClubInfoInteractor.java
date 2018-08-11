package de.heilsen.ganzhornfest.app.interactor;


import android.os.Handler;
import android.os.Looper;

import de.heilsen.ganzhornfest.domain.interactor.GetClubInfoInteractor;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class ThreadedGetClubInfoInteractor extends GetClubInfoInteractor {
    public ThreadedGetClubInfoInteractor(Repository<Club> clubRepository) {
        super(clubRepository);
    }

    @Override
    public void showInfo(final String clubName, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // run in main thread
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            // run in the worker thread
                            @Override
                            public void run() {
                                ThreadedGetClubInfoInteractor.super.showInfo(clubName, callback);
                            }
                        }
                );
            }
        }).run();

    }

}
