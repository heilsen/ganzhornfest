package de.heilsen.ganzhornfest.app.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import de.heilsen.ganzhornfest.app.ui.activity.TabbedActivity;

public class InsideTabbedActivityFragmentDelegate {

    private TabbedActivity tabbedActivity;
    private String toolbarTitle;
    private boolean showToolbarNavigationUp;

    public InsideTabbedActivityFragmentDelegate(Activity tabbedActivity, String toolbarTitle,
                                                boolean showToolbarNavigationUp) {
        if (!(tabbedActivity instanceof TabbedActivity)) throw new RuntimeException("Not the correct Activity.");
        this.tabbedActivity = (TabbedActivity) tabbedActivity;
        this.toolbarTitle = toolbarTitle;
        this.showToolbarNavigationUp = showToolbarNavigationUp;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        updateToolbar();
    }

    private void updateToolbar() {
        tabbedActivity.updateToolbar(toolbarTitle, showToolbarNavigationUp);
    }
}
