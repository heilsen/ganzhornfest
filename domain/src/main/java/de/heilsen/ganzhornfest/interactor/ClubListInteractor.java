package de.heilsen.ganzhornfest.interactor;


public class ClubListInteractor {
    private ListDisplayable listDisplayer;
    private DetailDisplayable detailDisplayer;

    public ClubListInteractor(ListDisplayable listDisplayer,
                              DetailDisplayable detailDisplayer) {
        this.listDisplayer = listDisplayer;
        this.detailDisplayer = detailDisplayer;
    }

    public void showList() {
        listDisplayer.showList();
    }

    public void select(int id) {
        detailDisplayer.showDetails(listDisplayer.getList().get(id));
    }
}
