package de.heilsen.ganzhornfest.app.ui.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import de.heilsen.ganzhornfest.app.R;
import de.heilsen.ganzhornfest.app.presenter.ListableItem;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;


public class ListableItemSection extends StatelessSection {
    private final String title;
    private final List<ListableItem> items;

    public ListableItemSection(String title, List<ListableItem> items) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_view_holder)
                .headerResourceId(R.layout.header_view_holder)
                .build());
        this.title = title;
        this.items = items;
    }

    @Override
    public int getContentItemsTotal() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ListItemViewHolder(view); //TODO: übergebe presenter
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemViewHolder viewHolder = (ListItemViewHolder) holder;
        viewHolder.render(items.get(position));
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new TextViewViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        TextViewViewHolder viewHolder = (TextViewViewHolder) holder;
        viewHolder.textView.setText(title);
    }
}
