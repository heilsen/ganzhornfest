package de.heilsen.ganzhornfest.app.ui.recyclerview;

import android.view.View;

import de.heilsen.ganzhornfest.app.presenter.ListableItem;
import de.heilsen.ganzhornfest.app.presenter.ListPresenter;

public class ListItemViewHolder extends TextViewViewHolder {

    ListItemViewHolder(View itemView) {
        super(itemView);
    }

    public void render(ListableItem listableItem) {
        renderText(listableItem);
    }

    private void renderText(ListableItem listableItem) {
        this.textView.setText(listableItem.getName());
    }

}
