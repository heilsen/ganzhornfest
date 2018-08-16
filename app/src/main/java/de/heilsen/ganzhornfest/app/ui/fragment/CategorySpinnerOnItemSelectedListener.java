package de.heilsen.ganzhornfest.app.ui.fragment;

import android.view.View;
import android.widget.AdapterView;

import de.heilsen.ganzhornfest.app.presenter.ListPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListableItemType;
import de.heilsen.ganzhornfest.domain.entity.OfferType;

import static de.heilsen.ganzhornfest.app.presenter.ListableItemType.ACTIONABLE_OFFER;
import static de.heilsen.ganzhornfest.app.presenter.ListableItemType.CLUB;
import static de.heilsen.ganzhornfest.app.presenter.ListableItemType.DRINK;
import static de.heilsen.ganzhornfest.app.presenter.ListableItemType.FOOD;

class CategorySpinnerOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
    private ListPresenter presenter;

    CategorySpinnerOnItemSelectedListener(ListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View item, int pos, long id) {
        switch (pos) {
            default:
            case 0:
                presenter.showListOfType(CLUB);
                break;
            case 1:
                presenter.showListOfType(FOOD);
                break;
            case 2:
                presenter.showListOfType(DRINK);
                break;
            case 3:
                presenter.showListOfType(ACTIONABLE_OFFER);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
