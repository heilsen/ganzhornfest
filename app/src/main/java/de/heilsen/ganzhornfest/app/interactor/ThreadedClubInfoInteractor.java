package de.heilsen.ganzhornfest.app.interactor;


import android.os.Handler;
import android.os.Looper;

import de.heilsen.ganzhornfest.domain.interactor.ClubInfoInteractor;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class ThreadedClubInfoInteractor extends ClubInfoInteractor {
    public ThreadedClubInfoInteractor(Repository<Club> clubRepository) {
        super(clubRepository);
    }

    @Override
    public void show(final String clubName, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // run in main thread
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            // run in the worker thread
                            @Override
                            public void run() {
                                ThreadedClubInfoInteractor.super.show(clubName, callback);
                            }
                        }
                );
            }
        }).run();

    }

}
