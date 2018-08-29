package de.heilsen.ganzhornfest.app.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Objects;

import de.heilsen.ganzhornfest.app.ui.activity.BottomNavActivity;

public class IsInBottomNavActivityFragmentDelegate {

    private BottomNavActivity bottomNavActivity;
    private String toolbarTitle;
    private boolean showToolbarNavigationUp;

    public IsInBottomNavActivityFragmentDelegate(Activity tabbedActivity, String toolbarTitle,
                                                 boolean showToolbarNavigationUp) {
        if (!(tabbedActivity instanceof BottomNavActivity)) throw new RuntimeException("Not the correct Activity.");
        this.bottomNavActivity = (BottomNavActivity) tabbedActivity;
        this.toolbarTitle = toolbarTitle;
        this.showToolbarNavigationUp = showToolbarNavigationUp;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        updateToolbar();
    }

    private void updateToolbar() {
        bottomNavActivity.updateToolbar(toolbarTitle, showToolbarNavigationUp);
    }

    public BottomNavActivity getTabbedActivity(Activity activity) {
        return Objects.requireNonNull((BottomNavActivity) activity);
    }
}
