package de.heilsen.ganzhornfest.app.ui.recyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.heilsen.ganzhornfest.R;

public class TextViewViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public TextViewViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.item_name_text_view);
    }


}
