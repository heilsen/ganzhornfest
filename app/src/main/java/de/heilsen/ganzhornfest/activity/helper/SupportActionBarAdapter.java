package de.heilsen.ganzhornfest.activity.helper;


import android.support.v7.app.ActionBar;

public class SupportActionBarAdapter {
    private ActionBar actionBar;

    public SupportActionBarAdapter(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    public void set(Callback callback) {
        if (actionBar != null) {
            callback.onActionBar(actionBar);
        }
    }

    public interface Callback {
        void onActionBar(ActionBar actionBar);
    }
}
