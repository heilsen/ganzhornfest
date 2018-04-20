package de.heilsen.ganzhornfest.activity;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.presenter.ClubListPresenter;

class ClubListViewHolder extends RecyclerView.ViewHolder {
    private ClubListPresenter presenter;
    private TextView clubName;
    private final TextView clubDescription;

    public ClubListViewHolder(View itemView, ClubListPresenter presenter) {
        super(itemView);
        this.presenter = presenter;
        clubName = itemView.findViewById(R.id.club_name_text_view);
        clubDescription = itemView.findViewById(R.id.club_description_text_view);
    }

    public void render(Club club) {
        hookListeners(club);
        renderText(club);
    }

    private void renderText(Club club) {
        clubName.setText(club.getName());
        clubDescription.setText(club.getDescription());
    }

    private void hookListeners(final Club club) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.select(club);
            }
        });
    }
}
