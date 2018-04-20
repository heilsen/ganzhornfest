package de.heilsen.ganzhornfest.interactor;


import de.heilsen.ganzhornfest.Club;

public class ShowClubDetail {
    private Club club;

    public ShowClubDetail(Club club) {
        this.club = club;
    }

    public void execute(Callback callback) {
        callback.show(club);
    }

    public interface Callback {
        void show(Club club);
    }
}
