package de.heilsen.ganzhornfest.domain.interactor;

import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class ClubListInteractor {
    private final Repository<Club> clubRepository;

    public ClubListInteractor(Repository<Club> clubRepository) {
        this.clubRepository = clubRepository;
    }


    public void listClubs(Callback callback) {
        callback.showClubList(clubRepository.getAll());
    }

    public void selectClub(String name, DetailCallback callback) {
        callback.showClubDetail(clubRepository.get(name));
    }


    public interface Callback {

        void showClubList(List<Club> clubList);
    }
    public interface DetailCallback {

        void showClubDetail(Club club);
    }

}