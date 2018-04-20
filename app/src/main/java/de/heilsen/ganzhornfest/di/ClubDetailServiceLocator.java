package de.heilsen.ganzhornfest.di;


import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.presenter.ClubDetailPresenter;

interface ClubDetailServiceLocator {
    ClubDetailPresenter clubDetailPresenter();
    void loadClub(Club club);
}
