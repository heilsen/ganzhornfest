package de.heilsen.ganzhornfest.app.ui.recyclerview;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.heilsen.ganzhornfest.app.R;
import de.heilsen.ganzhornfest.app.presenter.ListableItem;
import de.heilsen.ganzhornfest.app.presenter.ListPresenter;

public class ListableItemAdapter extends RecyclerView.Adapter<TextViewViewHolder> {
    private List<ListableItem> list;
    private ListPresenter presenter;

    public ListableItemAdapter(ListPresenter presenter) {
        this.presenter = presenter;
        this.list = new ArrayList<>();
    }

    public void set(Collection<ListableItem> listableItems) {
        list.clear();
        list.addAll(listableItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TextViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_holder, parent, false);
        return new PresentableListItemViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewViewHolder holder, int position) {
        PresentableListItemViewHolder presentableListItemViewHolder = (PresentableListItemViewHolder) holder;
        ListableItem listableItem = list.get(position);
        presentableListItemViewHolder.render(listableItem);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
