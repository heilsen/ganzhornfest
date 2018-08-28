package de.heilsen.ganzhornfest.app;

import android.app.Application;

import de.heilsen.ganzhornfest.app.di.ApplicationComponent;
import de.heilsen.ganzhornfest.app.di.DaggerApplicationComponent;
import de.heilsen.ganzhornfest.data.di.DaggerFakeRepositoryComponent;
import de.heilsen.ganzhornfest.data.di.DaggerObjectBoxRepositoryComponent;
import de.heilsen.ganzhornfest.data.di.FakeRepositoryModule;
import de.heilsen.ganzhornfest.data.di.ObjectBoxModule;
import de.heilsen.ganzhornfest.data.di.RepositoryComponent;


public class GanzhornfestApplication extends Application {

    private ApplicationComponent di;

    @Override
    public void onCreate() {
        super.onCreate();
        RepositoryComponent fakeRepositoryComponent = DaggerFakeRepositoryComponent.builder()
                .fakeRepositoryModule(new FakeRepositoryModule())
                .build();
        RepositoryComponent oBoxRepositoryComponent = DaggerObjectBoxRepositoryComponent.builder()
                .objectBoxModule(new ObjectBoxModule(this))
                .build();
        di = DaggerApplicationComponent.builder()
                .repositoryComponent(oBoxRepositoryComponent)
                .build();
    }

    public ApplicationComponent getDi() {
        return di;
    }
}
