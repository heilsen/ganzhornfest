package de.heilsen.ganzhornfest.app.ui.fragment;

import android.view.View;
import android.widget.AdapterView;

import de.heilsen.ganzhornfest.app.presenter.ListableItemsPresenter;
import de.heilsen.ganzhornfest.domain.entity.ListableItemType;

class CategorySpinnerOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
    private ListableItemsPresenter presenter;

    CategorySpinnerOnItemSelectedListener(ListableItemsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View item, int pos, long id) {
        switch (pos) {
            case 0:
                presenter.showListOfType(ListableItemType.CLUB);
                break;
            case 1:
            case 2:
            case 3:
            default:
                presenter.showListOfType(ListableItemType.FOOD);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
