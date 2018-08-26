package de.heilsen.ganzhornfest.domain.interactor;

import org.junit.Before;
import org.junit.Test;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.interactor.testdouble.FakeClubRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClubInfoInteractorTest {

    private FakeClubRepository clubRepository;
    private Club stpc;

    @Before
    public void setUp() throws Exception {
        Club soccerClub = new Club("Club Soccer", "Club 1 is awesome");
        Club lionsClub = new Club("Lions Club");
        stpc = new Club("St. Paulus Club", "Früher katholischer Jugendclub, jetzt nur noch Club");
        Club kolping = new Club("Kolping");
        clubRepository = new FakeClubRepository(soccerClub, lionsClub, stpc, kolping);
    }

    @Test
    public void showClub() {

        ClubInfoInteractor clubInfoInteractor = new ClubInfoInteractor(clubRepository);

        clubInfoInteractor.show("St. Paulus Club", new ClubInfoInteractor.Callback() {
            @Override
            public void show(Club club) {
                assertThat(club, is(stpc));
            }

            @Override
            public void showEmpty() {

            }
        });
    }
}