package de.heilsen.ganzhornfest.app.ui.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListableItem;
import de.heilsen.ganzhornfest.app.presenter.ListableItemType;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;


public class ListableItemSection extends StatelessSection {
    private final String title;
    private final List<ListableItem> items;
    private final ListableItemType listableItemType;
    private final DetailPresenter detailPresenter;

    public ListableItemSection(String title, List<ListableItem> items,
                               ListableItemType listableItemType,
                               DetailPresenter detailPresenter) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_view_holder)
                .headerResourceId(R.layout.header_view_holder)
                .build());
        this.title = title;
        this.items = items;
        this.listableItemType = listableItemType;
        this.detailPresenter = detailPresenter;
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
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ListItemViewHolder viewHolder = (ListItemViewHolder) holder;
        viewHolder.render(items.get(position));
        //TODO: needs refactoring becuase currently every click in the detail updates just the recyclerview and cannot be used out-of-the-box for navigation history
        //TODO: better use fragements, but that needs a rewrite
//        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                detailPresenter.show(listableItemType, items.get(position).getName());
//            }
//        });
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
