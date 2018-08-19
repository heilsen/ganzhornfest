package de.heilsen.ganzhornfest.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import de.heilsen.ganzhornfest.app.di.ApplicationComponent;
import de.heilsen.ganzhornfest.app.di.DaggerApplicationComponent;
import de.heilsen.ganzhornfest.data.di.DaggerFakeRepositoryComponent;
import de.heilsen.ganzhornfest.data.di.DaggerObjectBoxRepositoryComponent;
import de.heilsen.ganzhornfest.data.di.FakeRepositoryModule;
import de.heilsen.ganzhornfest.data.di.ObjectBoxModule;
import de.heilsen.ganzhornfest.data.di.ObjectBoxRepositoryComponent;
import de.heilsen.ganzhornfest.data.di.RepositoryComponent;


public class GanzhornfestApplication extends Application {

    private ApplicationComponent di;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        RepositoryComponent repositoryComponent = DaggerFakeRepositoryComponent.builder()
                .fakeRepositoryModule(new FakeRepositoryModule())
                .build();
        di = DaggerApplicationComponent.builder()
                .repositoryComponent(repositoryComponent)
                .build();
    }

    public ApplicationComponent getDi() {
        return di;
    }
}
