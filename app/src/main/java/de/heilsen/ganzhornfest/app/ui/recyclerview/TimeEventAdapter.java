package de.heilsen.ganzhornfest.app.ui.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.app.presenter.entity.TimeEvent;


public class TimeEventAdapter extends RecyclerView.Adapter<TimeEventViewHolder> {

    private List<TimeEvent> timeEvents = Collections.emptyList();

    public TimeEventAdapter() {
    }

    public void setTimeEvents(List<TimeEvent> timeEvents) {
        this.timeEvents = timeEvents;
    }

    @NonNull
    @Override
    public TimeEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_event_view_holder, viewGroup, false);
        return new TimeEventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeEventViewHolder timeEventViewHolder, int i) {
        timeEventViewHolder.bind(timeEvents.get(i));
    }

    @Override
    public int getItemCount() {
        return timeEvents.size();
    }
}
