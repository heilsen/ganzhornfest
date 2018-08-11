package de.heilsen.ganzhornfest.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.Objects;

import de.heilsen.ganzhornfest.app.ui.activity.TabbedActivity;

public abstract class InsideTabbedActivityFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateToolbar();
    }

    protected void updateToolbar() {
        getTabbedActivity().updateToolbar(getToolbarTitle(), showToolbarNavigationUp());
    }

    @NonNull
    public TabbedActivity getTabbedActivity() {
        return Objects.requireNonNull((TabbedActivity) getActivity());
    }

    protected abstract String getToolbarTitle();

    protected abstract boolean showToolbarNavigationUp();
}
