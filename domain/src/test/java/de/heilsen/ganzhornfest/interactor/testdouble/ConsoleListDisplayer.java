package de.heilsen.ganzhornfest.interactor.testdouble;


import java.util.List;

import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.interactor.ListDisplayable;
import de.heilsen.ganzhornfest.repository.ClubRepository;

public class ConsoleListDisplayer implements ListDisplayable {

    private List<Club> items;
    private boolean isShowingList = false;

    public ConsoleListDisplayer(ClubRepository clubRepository) {
        this.items = clubRepository.getAll();
    }

    @Override
    public void showList() {
        for (int i = 0; i < items.size(); i++) {
            printItem(items.get(i), i);
        }
        isShowingList = true;
    }

    private void printItem(Club club, int id) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(" ")
                .append(id)
                .append(" ")
                .append(club.getName());
        if (club.getDescription() != null && !club.getDescription().equals("")) {
            stringBuilder
                    .append(" (")
                    .append(club.getDescription())
                    .append(")");
        }
        stringBuilder
                .append("\n");
        System.out.print(stringBuilder.toString());
    }

    public boolean isShowingList() {
        return isShowingList;
    }

    @Override
    public List<Club> getList() {
        return items;
    }
}
