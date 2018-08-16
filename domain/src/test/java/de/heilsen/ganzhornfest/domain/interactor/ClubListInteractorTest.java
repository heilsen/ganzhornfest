package de.heilsen.ganzhornfest.domain.interactor;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.interactor.testdouble.FakeClubRepository;
import de.heilsen.ganzhornfest.domain.repository.Repository;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ClubListInteractorTest {

    private ClubListInteractor clubListInteractor;
    private Repository<Club> clubRepository;
    private Club stpc;

    @Before
    public void setUp() throws Exception {
        Club soccerClub = new Club("Club Soccer", "Club 1 is awesome");
        Club lionsClub = new Club("Lions Club");
        stpc = new Club("St. Paulus Club", "Früher katholischer Jugendclub, jetzt nur noch Club");
        Club kolping = new Club("Kolping");
        clubRepository = new FakeClubRepository(soccerClub, lionsClub, stpc, kolping);
        clubListInteractor = new ClubListInteractor(clubRepository);
    }

    @Test
    public void listClubs() {
        clubListInteractor.listClubs(new ClubListInteractor.Callback() {
            @Override
            public void showClubList(List<Club> clubList) {
                List<Club> expectedClubList = clubRepository.getAll();
                assertThat(clubList, hasItem(stpc));
                assertThat(clubList, is(expectedClubList));
            }
        });

    }

    @Test
    public void selectClub() {
        clubListInteractor.selectClub(stpc.getName(), new ClubListInteractor.DetailCallback() {
            @Override
            public void showClubDetail(Club club) {
                assertThat(club, is(stpc));
            }
        });
    }
}