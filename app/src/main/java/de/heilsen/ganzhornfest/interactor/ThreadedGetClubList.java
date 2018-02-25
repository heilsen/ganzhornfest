package de.heilsen.ganzhornfest.interactor;


import android.os.Handler;
import android.os.Looper;

import de.heilsen.ganzhornfest.repository.ClubRepository;

public class ThreadedGetClubList extends GetClubList {
    public ThreadedGetClubList(ClubRepository clubRepository) {
        super(clubRepository);
    }

    @Override
    public void getAll(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //runs in main Thread
                onGetFinished(callback);
            }
        }).run();
    }

    private void onGetFinished(final Callback callback) {
        new Handler(Looper.getMainLooper()).post(
                //runs in current thread
                new Runnable() {

                    @Override
                    public void run() {
                        onGetStarted(callback);
                    }
                }
        );
    }

    private void onGetStarted(final Callback callback) {
        super.getAll(callback);
    }
}
