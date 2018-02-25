package de.heilsen.ganzhornfest;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class ClubListViewHolder extends RecyclerView.ViewHolder {
    private ClubListPresenter presenter;
    private TextView clubName;

    public ClubListViewHolder(View itemView, ClubListPresenter presenter) {
        super(itemView);
        this.presenter = presenter;
        clubName = itemView.findViewById(R.id.clubNameTextView);
    }

    public void render(Club club) {
        hookListeners(club);
        renderText(club);
    }

    private void renderText(Club club) {
        clubName.setText(club.getName());
    }

    private void hookListeners(final Club club) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClubClicked(club);
            }
        });
    }
}
