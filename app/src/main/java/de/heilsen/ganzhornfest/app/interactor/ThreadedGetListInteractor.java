package de.heilsen.ganzhornfest.app.interactor;

import android.os.Handler;
import android.os.Looper;

import de.heilsen.ganzhornfest.domain.entity.ListableItemType;
import de.heilsen.ganzhornfest.domain.repository.RepositoryProvider;
import de.heilsen.ganzhornfest.domain.interactor.GetListInteractor;

//TODO: replace with RxJava
public class ThreadedGetListInteractor extends GetListInteractor {
    public ThreadedGetListInteractor(RepositoryProvider repositoryProvider) {
        super(repositoryProvider);
    }

    @Override
    public void selectList(final ListableItemType type, final Callback callback) {
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
                                ThreadedGetListInteractor.super.selectList(type, callback);
                            }
                        }
                );
            }
        }).run();
    }

}
