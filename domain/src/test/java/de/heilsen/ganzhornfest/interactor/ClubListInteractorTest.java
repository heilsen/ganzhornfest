package de.heilsen.ganzhornfest.interactor;

import org.junit.Before;
import org.junit.Test;

import de.heilsen.ganzhornfest.interactor.testdouble.ConsoleDetailDisplayer;
import de.heilsen.ganzhornfest.interactor.testdouble.ConsoleListDisplayer;
import de.heilsen.ganzhornfest.interactor.testdouble.FakeClubRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

public class ClubListInteractorTest {

    private ClubListInteractor clubListInteractor;
    private ConsoleListDisplayer listDisplayer;
    private ConsoleDetailDisplayer detailDisplayer;

    @Before
    public void setUp() throws Exception {
        FakeClubRepository clubRepository = new FakeClubRepository();
        detailDisplayer = new ConsoleDetailDisplayer();
        listDisplayer = new ConsoleListDisplayer(clubRepository);
        clubListInteractor = new ClubListInteractor(listDisplayer, detailDisplayer);
    }

    @Test
    public void shouldInit() throws Exception {
        assertThat(clubListInteractor, isA(ClubListInteractor.class));
    }

    @Test
    public void shouldShowListOnDisplayable() throws Exception {
        clubListInteractor.showList();
        assertThat(listDisplayer.isShowingList(), is(true));
    }
    @Test
    public void shouldShowDetail() throws Exception {
        int givenId = 1;
        clubListInteractor.select(givenId);
        assertThat(detailDisplayer.isShowingDetail(listDisplayer.getList().get(givenId)), is(true));
    }
}