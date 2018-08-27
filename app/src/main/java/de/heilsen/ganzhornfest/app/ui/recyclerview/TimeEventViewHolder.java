package de.heilsen.ganzhornfest.app.ui.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.app.presenter.entity.TimeEvent;

public class TimeEventViewHolder extends RecyclerView.ViewHolder {
    public TimeEventViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(TimeEvent timeEvent) {
        TextView time = itemView.findViewById(R.id.time_text_view);
        TextView description = itemView.findViewById(R.id.description_text_view);
        TextView location = itemView.findViewById(R.id.location_text_view);

        time.setText(timeEvent.getTime());

        if (timeEvent.hasDescription()) {
            description.setVisibility(View.VISIBLE);
            description.setText(timeEvent.getDescription());
        }

        if (timeEvent.hasLocation()) {
            location.setVisibility(View.VISIBLE);
            location.setText(timeEvent.getLocation());
        }
    }
}
