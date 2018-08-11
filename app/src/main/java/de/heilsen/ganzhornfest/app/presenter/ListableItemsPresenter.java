package de.heilsen.ganzhornfest.app.presenter;

import java.util.List;
import java.util.Map;

import de.heilsen.ganzhornfest.domain.entity.ListableItem;
import de.heilsen.ganzhornfest.domain.entity.ListableItemType;
import de.heilsen.ganzhornfest.domain.interactor.GetListInteractor;

public class ListableItemsPresenter extends Presenter<ListableItemsPresenter.View> {

    private GetListInteractor getListInteractor;
    private ListableItemType itemType;

    public ListableItemsPresenter(GetListInteractor getListInteractor) {
        this.getListInteractor = getListInteractor;
    }

    public void showListOfType(ListableItemType itemType) {
        this.itemType = itemType;
        getView().showLoading();
        getListInteractor.selectList(itemType,
                new GetListInteractor.Callback() {
                    @Override
                    public void onItemListLoaded(List<? extends ListableItem> listableItems) {
                        getView().showList(listableItems);
                        getView().hideLoading();
                    }
                });
    }

    public void showItem(final ListableItem listableItem) {
        final Class<? extends ListableItem> aClass = listableItem.getClass();
        getListInteractor.selectItem(listableItem, new GetListInteractor.DetailCallback() {
            public void onItemSelected(Map<ListableItemType, List<? extends ListableItem>> correspondingItems) {
                getView().openItemDetail(itemType, listableItem.getName());
            }
        });

    }

    public interface View extends Presenter.View {
        void showList(List<? extends ListableItem> listableItems);

        void openItemDetail(ListableItemType itemType, String name);
    }
}
