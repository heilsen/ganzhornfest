package de.heilsen.ganzhornfest.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import de.heilsen.ganzhornfest.app.di.ApplicationComponent;
import de.heilsen.ganzhornfest.app.di.ComponentModule;
import de.heilsen.ganzhornfest.app.di.DaggerApplicationComponent;


public class GanzhornfestApplication extends Application {

    private ApplicationComponent di;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        di = DaggerApplicationComponent.builder().componentModule(new ComponentModule(this)).build();
    }

    public ApplicationComponent getDi() {
        return di;
    }
}
