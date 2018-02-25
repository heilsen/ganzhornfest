package de.heilsen.ganzhornfest.interactor;

import java.util.List;

import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.repository.ClubRepository;

public class GetClubList {
    private ClubRepository clubRepository;

    public GetClubList(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public void getAll(Callback callback) {
        List<Club> clubList = clubRepository.getAll();
        callback.onClubListLoaded(clubList);

    }

    public interface Callback {
        void onClubListLoaded(List<Club> clubList);
    }
}