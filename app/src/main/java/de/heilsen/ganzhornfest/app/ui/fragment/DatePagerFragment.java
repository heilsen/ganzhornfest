package de.heilsen.ganzhornfest.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import de.heilsen.ganzhornfest.R;

import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SEPTEMBER;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;

public class DatePagerFragment extends IsInBottomNavActivityFragment {
    public static final String TAG = "DatePagerFragment";
    private static final String ARG_TITLE = "ARG_TITLE";
    private static final String ARG_TYPE = "ARG_TYPE";

    public DatePagerFragment() {
        // Required empty public constructor
    }

    public static DatePagerFragment newInstance(String title, String type) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_TYPE, type);
        DatePagerFragment fragment = new DatePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_pager, container, false);
        ViewPager pager = view.findViewById(R.id.pager);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        Calendar saturdayCalendar = Calendar.getInstance();
        saturdayCalendar.set(2018,SEPTEMBER,1);
        Calendar sundayCalendar = Calendar.getInstance();
        sundayCalendar.set(2018,SEPTEMBER,2);
        Calendar mondayCalendar = Calendar.getInstance();
        mondayCalendar.set(2018,SEPTEMBER,3);
        pager.setAdapter(new TabbedDateItemsPlainListFragmentPagerAdapter(
                getChildFragmentManager(),
                Arrays.asList(saturdayCalendar, sundayCalendar, mondayCalendar)));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected String getToolbarTitle() {
        if (getArguments() != null) {
            return getArguments().getString(ARG_TITLE);
        } else {
            return "";
        }
    }

    @Override
    protected boolean showToolbarNavigationUp() {
        return false;
    }

    private class TabbedDateItemsPlainListFragmentPagerAdapter extends FragmentPagerAdapter {

        private final List<Calendar> calendarList;

        TabbedDateItemsPlainListFragmentPagerAdapter(FragmentManager fragmentManager, List<Calendar> calendarList) {
            super(fragmentManager);
            this.calendarList = calendarList;
        }

        @Override
        public Fragment getItem(int itemId) {
            return ListTimeEventsByDateFragment.newInstance(calendarList.get(itemId), getArguments().getString(ARG_TYPE));
        }

        @Override
        public int getCount() {
            return calendarList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return convertToDayOfWeekString(calendarList.get(position));
        }

    }

    private static String convertToDayOfWeekString(Calendar calendar) {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case MONDAY: return "Montag";
            case TUESDAY: return "Dienstag";
            case WEDNESDAY: return "Mittwoch";
            case THURSDAY: return "Donnerstag";
            case FRIDAY: return "Freitag";
            case SATURDAY: return "Samstag";
            case SUNDAY: return "Sonntag";
        }
        return "Unbekannt";
    }
}
