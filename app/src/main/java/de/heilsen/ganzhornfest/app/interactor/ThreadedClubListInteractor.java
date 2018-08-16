package de.heilsen.ganzhornfest.app.interactor;

import android.os.Handler;
import android.os.Looper;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.interactor.ClubListInteractor;
import de.heilsen.ganzhornfest.domain.repository.Repository;

//TODO: replace with RxJava
public class ThreadedClubListInteractor extends ClubListInteractor {
    public ThreadedClubListInteractor(Repository<Club> clubRepository) {
        super(clubRepository);
    }

    @Override
    public void listClubs(final Callback callback) {
        //run in worker thread:
        new Thread(new Runnable() {
            @Override
            public void run() {
                //runs in main Thread:
                new Handler(Looper.getMainLooper()).post(
                        //runs in current worker thread:
                        new Runnable() {
                            @Override
                            public void run() {
                                ThreadedClubListInteractor.super.listClubs(callback);
                            }
                        }
                );
            }
        }).run();
    }

    @Override
    public void selectClub(final String name, final DetailCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                ThreadedClubListInteractor.super.selectClub(name, callback);
                            }
                        }
                );
            }
        }).run();

    }
}
