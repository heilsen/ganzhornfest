package de.heilsen.ganzhornfest;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ClubListAdapter extends RecyclerView.Adapter<ClubListViewHolder> {
    private List<Club> clubList;
    private ClubListPresenter presenter;

    public ClubListAdapter(ClubListPresenter presenter) {
        this.presenter = presenter;
        this.clubList = new ArrayList<>();
    }

    public void addAll(Collection<Club> clubs) {
        clubList.addAll(clubs);
    }

    @Override
    public ClubListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_list_view_holder_layout, parent, false);
        return new ClubListViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(ClubListViewHolder holder, int position) {
        ClubListViewHolder clubListViewHolder = holder;
        Club club = clubList.get(position);
        clubListViewHolder.render(club);
    }


    @Override
    public int getItemCount() {
        return clubList.size();
    }
}
