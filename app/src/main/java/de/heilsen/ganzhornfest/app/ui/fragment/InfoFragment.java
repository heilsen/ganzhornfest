package de.heilsen.ganzhornfest.app.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.heilsen.ganzhornfest.app.R;

public class InfoFragment extends InsideTabbedActivityFragment {
    public static final String TAG = "InfoFragment";
    private int bulletPointGapWidth;

    public InfoFragment() {
    }

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bulletPointGapWidth = Math.round(5f * getResources().getDisplayMetrics().scaledDensity); // == 5dp * density
        return inflater.inflate(R.layout.info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDateRangeText();
        setOpeningTimeText();
        setLocationText();
        setListingText();
        setEventText();
        setBusText();
    }

    private void setDateRangeText() {
        TextView dateDurationTextView = getTabbedActivity().findViewById(R.id.eventDateRange);
        dateDurationTextView.setText(Html.fromHtml("<b>1. bis 3. September 2018"));
    }

    private void setOpeningTimeText() {
        TextView openingTimeTextView = getTabbedActivity().findViewById(R.id.eventOpeningTime);
        openingTimeTextView.setText(
                Html.fromHtml(
                        "<b>Samstag</b><br>16:00 - 01:00 Uhr<br>" +
                                "<b>Sonntag</b><br>11:00 - 24:00 Uhr<br>" +
                                "<b>Montag</b><br>11:00-24:00 Uhr"));
    }

    private void setLocationText() {
        TextView locationTextView = getTabbedActivity().findViewById(R.id.eventLocationInfo);
        locationTextView.setText(Html.fromHtml("<b>Neckarsulm</b><br>zwischen Deutschordensschloss und Marktplatz"));
    }

    private void setListingText() {
        TextView clubAndOffersTextView = getTabbedActivity().findViewById(R.id.eventListingInfo);
        new RelativeSizeSpan(0f);
        SpannableString spannableString = new SpannableString(
                "Unsere Neckarsulmer Vereine bieten:\n" +
                "über 100 unterschiedliche Speisen\n" +
                "über 40 unterschiedliche Getränke");
        spannableString.setSpan(new BulletSpan(bulletPointGapWidth), 36, 69, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new BulletSpan(bulletPointGapWidth), 70, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        clubAndOffersTextView.setText(spannableString);
    }

    private void setEventText() {
        TextView programInfoTextView = getTabbedActivity().findViewById(R.id.eventProgramInfo);
        SpannableString spannableString = new SpannableString(
                "Kunst- und Kulturprogramm:\n" +
                        "über 30 künstlerische Programmpunkte\n" +
                        "über 10 Programmpunkte für Kinder/Jugendliche");
        spannableString.setSpan(new BulletSpan(bulletPointGapWidth), 27, 63, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new BulletSpan(bulletPointGapWidth), 64, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        programInfoTextView.setText(spannableString);
    }

    private void setBusText() {
        TextView busInfoTextView = getTabbedActivity().findViewById(R.id.eventBusInfo);
        String source = "An den drei Festtagen dürfen alle Busse in Neckarsulm kostenlos genutzt werden (gilt " +
                "nicht für Rufauto-Fahrten). Dieser Service wird Dir ermöglicht durch die Stadt Neckarsulm, " +
                "den HNV und die Busunternehmen OVR, RBS und Zartmann. Die Fahrpläne findest auf den " +
                "Aushängen an den Haltestellen und unter https://www.neckarsulmer-stadtbus.de.\n\n" +
                "Eine Übersicht über die Rückfahrt vom Ganzhornfest mit dem Bus findest Du auch hier in " +
                "der App.";
        busInfoTextView.setText(source);
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.title_fragment_info);
    }

    @Override
    protected boolean showToolbarNavigationUp() {
        return false;
    }

}
