package de.heilsen.ganzhornfest.app.di;


import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.interactor.GetClubInfoInteractor;

interface DetailServiceLocator {
    DetailPresenter detailPresenter();
    GetClubInfoInteractor getClubInfoInteractor();
}
