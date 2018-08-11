package de.heilsen.ganzhornfest.app.ui.recyclerview;

import android.view.View;

import de.heilsen.ganzhornfest.app.presenter.ListableItemsPresenter;
import de.heilsen.ganzhornfest.domain.entity.ListableItem;

public class PresentableListItemViewHolder extends TextViewViewHolder {
    private ListableItemsPresenter presenter;

    PresentableListItemViewHolder(View itemView, ListableItemsPresenter presenter) {
        super(itemView);
        this.presenter = presenter;

    }

    public void render(ListableItem listableItem) {
        registerListeners(listableItem);
        renderText(listableItem);
    }
    private void registerListeners(final ListableItem listableItem) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showItem(listableItem);
            }
        });
    }

    private void renderText(ListableItem listableItem) {
        this.textView.setText(listableItem.getName());
    }

}
