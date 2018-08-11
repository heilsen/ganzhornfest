package de.heilsen.ganzhornfest.app.presenter;


import java.util.List;
import java.util.Map;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.ListableItem;
import de.heilsen.ganzhornfest.domain.entity.ListableItemType;
import de.heilsen.ganzhornfest.domain.interactor.GetClubInfoInteractor;

public class DetailPresenter extends Presenter<DetailPresenter.View> {
    private GetClubInfoInteractor getClubInfoInteractor;

    public DetailPresenter(GetClubInfoInteractor getClubInfoInteractor) {
        this.getClubInfoInteractor = getClubInfoInteractor;
    }

    public void show(String name) {
        getView();
        getClubInfoInteractor.showInfo(name, new GetClubInfoInteractor.Callback() {
            @Override
            public void show(Club club, Map<ListableItemType, List<? extends ListableItem>> infoMap) {
                if (club != null) {
                    getView().showDetail(infoMap);
                } else {
                    getView().showEmpty();
                }
            }

        });
    }

    public interface View extends Presenter.View {
        void showDetail(Map<ListableItemType, List<? extends ListableItem>> infoMap);
        void showEmpty();
    }
}
