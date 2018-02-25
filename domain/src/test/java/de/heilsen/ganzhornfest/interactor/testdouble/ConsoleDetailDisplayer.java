package de.heilsen.ganzhornfest.interactor.testdouble;


import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.interactor.DetailDisplayable;

public class ConsoleDetailDisplayer implements DetailDisplayable {

    private Club currentClub;

    public ConsoleDetailDisplayer() {
    }

    @Override
    public void showDetails(Club club) {
        String detailString = "Selected: " + club;
        System.out.println(detailString);
        currentClub = club;
    }

    public boolean isShowingDetail(Club club) {
        return club.equals(currentClub);
    }
}

