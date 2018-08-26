package de.heilsen.ganzhornfest.app.ui.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.heilsen.ganzhornfest.R;

public class StringListAdapter extends RecyclerView.Adapter<TextViewViewHolder> {

    private List<String> stringList;

    public StringListAdapter() {
    }

    @NonNull
    @Override
    public TextViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewHolderView =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_holder, viewGroup, false);
        return new TextViewViewHolder(viewHolderView);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewViewHolder viewHolder, int i) {
        viewHolder.textView.setText(stringList.get(i));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }
}
