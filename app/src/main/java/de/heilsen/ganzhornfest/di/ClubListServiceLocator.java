package de.heilsen.ganzhornfest.di;


import de.heilsen.ganzhornfest.activity.ClubListAdapter;
import de.heilsen.ganzhornfest.presenter.ClubListPresenter;

interface ClubListServiceLocator {

    ClubListPresenter clubListPresenter();

    ClubListAdapter clubListAdapter();
}
