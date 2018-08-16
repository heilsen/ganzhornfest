package de.heilsen.ganzhornfest.app.ui.recyclerview;

import android.view.View;

import de.heilsen.ganzhornfest.app.presenter.ListableItem;
import de.heilsen.ganzhornfest.app.presenter.ListPresenter;

public class PresentableListItemViewHolder extends TextViewViewHolder {
    private ListPresenter presenter;

    PresentableListItemViewHolder(View itemView, ListPresenter presenter) {
        super(itemView);
        this.presenter = presenter;
    }

    public void render(ListableItem listableItem) {
        renderText(listableItem);
    }

    private void renderText(ListableItem listableItem) {
        this.textView.setText(listableItem.getName());
    }

}
