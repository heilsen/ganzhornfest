package de.heilsen.ganzhornfest.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.app.GanzhornfestApplication;
import de.heilsen.ganzhornfest.app.di.ApplicationComponent;
import de.heilsen.ganzhornfest.app.presenter.ListPointInTimePresenter;
import de.heilsen.ganzhornfest.app.presenter.entity.TimeEvent;
import de.heilsen.ganzhornfest.app.ui.recyclerview.TimeEventAdapter;

public class ListTimeEventsByDateFragment extends Fragment implements ListPointInTimePresenter.View {

    private static final String ARG_DATE = "date";
    private static final String ARG_TYPE = "type";
    @Inject
    @Named("program")
    ListPointInTimePresenter programListDateItemPresenter;
    @Inject
    @Named("bus")
    ListPointInTimePresenter busListDateItemPresenter;
    private TimeEventAdapter adapter = new TimeEventAdapter();
    private ListPointInTimePresenter listPointInTimePresenter;
    private RecyclerView recyclerView;

    public static ListTimeEventsByDateFragment newInstance(Calendar calendar, String type) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, calendar);
        args.putString(ARG_TYPE, type);
        ListTimeEventsByDateFragment fragment = new ListTimeEventsByDateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plain_list, container, false);

        ApplicationComponent di = ((GanzhornfestApplication) getActivity().getApplication()).getDi();
        di.inject(this);
        if (getArguments().getString(ARG_TYPE).equals("program")) {
            listPointInTimePresenter = programListDateItemPresenter;
        } else {
            listPointInTimePresenter = busListDateItemPresenter;
        }
        listPointInTimePresenter.attachView(this);
        Calendar calendar = (Calendar) getArguments().getSerializable(ARG_DATE);
        listPointInTimePresenter.showEventsForDate(calendar);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showEvents(List<TimeEvent> events) {
        adapter.setTimeEvents(events);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}

