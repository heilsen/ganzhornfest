package de.heilsen.ganzhornfest.interactor;


import android.os.Handler;
import android.os.Looper;


import de.heilsen.ganzhornfest.Club;

public class ThreadedShowClubDetail extends ShowClubDetail {
    public ThreadedShowClubDetail(Club club) {
        super(club);
    }

    @Override
    public void execute(final Callback callback) {
        // run in worker thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // run in main thread
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            // run in the worker thread
                            @Override
                            public void run() {
                                ThreadedShowClubDetail.super.execute(callback);
                            }
                        }
                );
            }
        }).run();
    }
}
