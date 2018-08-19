package de.heilsen.ganzhornfest.domain.interactor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class ClubListInteractor {
    private final Repository<Club> clubRepository;

    public ClubListInteractor(Repository<Club> clubRepository) {
        this.clubRepository = clubRepository;
    }


    public void listClubs(Callback callback) {
        callback.showClubList(sort(clubRepository.getAll()));
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

    private static List<Club> sort(List<Club> list) {
        //noinspection Java8ListSort, doesn't work for API<24
        Collections.sort(list, new Comparator<Club>() {
            @Override
            public int compare(Club club1, Club club2) {
                return club1.getName().compareTo(club2.getName());
            }
        });
        return list;
    }

}