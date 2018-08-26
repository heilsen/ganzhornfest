package de.heilsen.ganzhornfest.app.ui.recyclerview;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.app.presenter.ListableItem;
import de.heilsen.ganzhornfest.app.presenter.ListPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListableItemType;
import de.heilsen.ganzhornfest.domain.interactor.ClubListInteractor;

public class ListableItemAdapter extends RecyclerView.Adapter<TextViewViewHolder> {
    private List<ListableItem> list;
    private ListPresenter listPresenter;
    private ListableItemType listableItemType;

    public ListableItemAdapter() {
        this.list = new ArrayList<>();
    }

    public void updateList(Collection<ListableItem> listableItems, ListPresenter listPresenter, ListableItemType listableItemType) {
        this.listPresenter = listPresenter;
        this.listableItemType = listableItemType;
        list.clear();
        list.addAll(listableItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TextViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_holder, parent, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewViewHolder holder, int position) {
        ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
        final ListableItem listableItem = list.get(position);
        listItemViewHolder.render(listableItem);
        listItemViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPresenter.showItem(listableItemType, listableItem);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
