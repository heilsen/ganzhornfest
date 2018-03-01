package de.heilsen.ganzhornfest.interactor;


import android.os.Handler;
import android.os.Looper;

import de.heilsen.ganzhornfest.repository.ClubRepository;

public class ThreadedGetClubList extends GetClubList {
    public ThreadedGetClubList(ClubRepository clubRepository) {
        super(clubRepository);
    }

    @Override
    public void execute(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //runs in main Thread
                onFinished(callback);
            }
        }).run();
    }

    private void onFinished(final Callback callback) {
        new Handler(Looper.getMainLooper()).post(
                //runs in current thread
                new Runnable() {

                    @Override
                    public void run() {
                        onStarted(callback);
                    }
                }
        );
    }

    private void onStarted(final Callback callback) {
        super.execute(callback);
    }
}
