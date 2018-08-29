package de.heilsen.ganzhornfest.app.di;

import javax.inject.Singleton;

import dagger.Component;
import de.heilsen.ganzhornfest.app.presenter.ClubListPresenter;
import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListPresenter;
import de.heilsen.ganzhornfest.app.ui.fragment.ListTimeEventsByDateFragment;
import de.heilsen.ganzhornfest.app.ui.recyclerview.ListableItemAdapter;
import de.heilsen.ganzhornfest.data.di.RepositoryComponent;

@Singleton
@Component(modules = ComponentModule.class, dependencies = RepositoryComponent.class)
public interface ApplicationComponent {
    DetailPresenter detailPresenter();

    ListPresenter listableItemPresenter();

    ListableItemAdapter clubListAdapter();

    ClubListPresenter clubListPresenter();

    void inject(ListTimeEventsByDateFragment listTimeEventsByDateFragment);
}
