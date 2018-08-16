package de.heilsen.ganzhornfest.app.di;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListPresenter;
import de.heilsen.ganzhornfest.app.ui.recyclerview.ListableItemAdapter;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.repository.Repository;

@Singleton
@Component(modules = ComponentModule.class)
public interface ApplicationComponent {
    Repository<Club> clubRepository();

    DetailPresenter detailPresenter();

    ListPresenter listableItemPresenter();

    ListableItemAdapter clubListAdapter();

}
