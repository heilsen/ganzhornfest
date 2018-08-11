package de.heilsen.ganzhornfest.app.di;


import de.heilsen.ganzhornfest.app.presenter.ListableItemsPresenter;
import de.heilsen.ganzhornfest.app.ui.recyclerview.ListableItemAdapter;

interface ListableItemsServiceLocator {

    ListableItemsPresenter clubListPresenter();

    ListableItemAdapter clubListAdapter();
}
