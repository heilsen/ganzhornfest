package de.heilsen.ganzhornfest.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.app.GanzhornfestApplication;
import de.heilsen.ganzhornfest.app.di.ApplicationComponent;
import de.heilsen.ganzhornfest.app.presenter.ClubListPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListableItemType;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.GeoLocation;

//TODO: handle no google play services
//TODO: handle outdated play servies
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, ClubListPresenter.View,
        GoogleMap.OnInfoWindowClickListener {
    public static final String TAG = "MapFragment";
    private IsInBottomNavActivityFragmentDelegate isInBottomNavActivityFragmentDelegate;
    private ClubListPresenter clubListPresenter;
    private List<Club> clubList;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApplicationComponent di = ((GanzhornfestApplication) getActivity().getApplication()).getDi();
        clubListPresenter = di.clubListPresenter();
        clubListPresenter.attachView(this);
        clubListPresenter.showList();
        getTabbedDelegate().onViewCreated(view, savedInstanceState);
    }

    protected IsInBottomNavActivityFragmentDelegate getTabbedDelegate() {
        if (isInBottomNavActivityFragmentDelegate == null) {
            isInBottomNavActivityFragmentDelegate =
                    new IsInBottomNavActivityFragmentDelegate(getActivity(), "Karte", false);
        }
        return isInBottomNavActivityFragmentDelegate;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapRestrictions(googleMap, 17, true, buildLatLngBounds(clubList));
        addMarkers(googleMap, clubList);
        googleMap.setOnInfoWindowClickListener(this);


        /*
49.19229    9.223065    Arbeiter-Samariter-Bund (ASB)  club
49.191768   9.224544    Bowlingsportverein (BSV)       club
49.191959   9.222619    Centro Italiano                club
49.191662   9.224517    DLRG                           club
49.1916077  9.2226579   DLRG                           club
49.191618   9.225298    Downtownboys                   club
49.191957   9.222933    DRK Ortsverein                 club
49.192041   9.222668    Fischerei- und Sportangler-Ve  club
49.19111    9.222178    Freier Kindergarten Neckarsul  club
49.192071   9.223429    Georgspfadfinder               club
49.192345   9.222834    Gesangsverein Concordia        club
49.191917   9.22237     Gesangsverein Lassallia        club
49.191196   9.2225      Griechische Gemeinde           club
49.191778   9.222837    Harmonika-Club                 club
49.191543   9.225009    Jugendfarm                     club
49.19111    9.222048    Jugendfarm                     club
49.191687   9.2223      Junge Union Stadtverband       club
49.191517   9.222594    Jusos                          club
49.191805   9.224282    Katholischer Kirchenchor St.   club
49.191779   9.224976    KIWANIS                        club
49.1914438  9.222666    Kolping Blasorchester          club
49.192164   9.22317     Kolpingsfamilie                club
49.192052   9.2229932   Kolping Jugend                 club
49.191367   9.222389    Kreatief - Kultur im Unterlan  club
49.191443   9.224773    Metropolitan Jazz Community    club
49.1915472  9.2233634   NSU - Fußball Aktive           club
49.191535   9.22344     NSU - Jugend-Fußball           club
49.1917453  9.2253106   NSU - Handball                 club
49.1917584  9.2252637   NSU - Handball                 club
49.1917856  9.2251376   NSU - Handball                 club
49.191452   9.222574    NSU - Kanu                     club
49.191638   9.222142    NSU - Karate                   club
49.191584   9.225269    NSU - Leichtathletik           club
49.191691   9.222882    NSU - Rugby                    club
49.191682   9.224902    NSU - Tischtennis 1            club
49.191566   9.222624    NSU - Tischtennis 2            club
49.191835   9.224575    Tierschutzverein Pfötchenhilf  club
49.192232   9.222689    Sängerbund                     club
49.192161   9.22278     SC Amorbach                    club
49.191691   9.222882    Schützengilde                  club
49.1915875  9.2227491   Schützengilde                  club
49.192001   9.22296     SPD Ortsverein                 club
49.191693   9.222072    Stone Heads                    club
49.191992   9.223657    St. Paulus Club                club
49.191405   9.22337     Sulmanafetza                   club
49.191856   9.222512    Tauchclub Walhai               club
49.191428   9.223834    TC Sulmtal                     club
49.1913447  9.2237053   TC Sulmtal                     club
49.191808   9.222764    Türkspor Neckarsulm            club
49.19185    9.222794    Türkspor Neckarsulm - Jugenda  club
49.191459   9.223019    UFC                            club
49.191715   9.224514    Waldkindergarten Waldzauber    club
49.191197   9.222202    Waldkindergarten Waldzauber    club
49.191996   9.222762    Weinbauverein                  club
49.191853   9.224752    Karussell                      playground
49.192436   9.223148    Eisenbähnle                    playground
49.191764   9.223357    Kinder-Flohmarkt               playground
49.191197   9.222202    Waldkindergarten               playground
49.191756   9.225421    WC                             toilets
49.192685   9.222914    WC                             toilets
49.192106   9.222767    WC                             toilets
49.19146    9.221921    WC                             toilets
49.191238   9.222961    WC                             toilets
49.191036   9.222433    WC                             toilets
49.192083   9.222574    Museumsplatz                   stage
49.191665   9.225433    Marktplatz                     stage
49.191405   9.222139
Karlsplatz                    stage
49.19146    9.2248      Metropolitan Jazz Community    stage
49.192608   9.223059    Erste-Hilfe (ASB)              first_aid
49.193846   9.227222    ZOB (Ballei)                   busstop
         */
    }

    private void mapRestrictions(GoogleMap googleMap, int minZoom, boolean showZoomButtons,
                                 LatLngBounds latLngBounds) {
        googleMap.setMinZoomPreference(minZoom);
        googleMap.getUiSettings().setZoomControlsEnabled(showZoomButtons);
        googleMap.setLatLngBoundsForCameraTarget(latLngBounds);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngBounds.getCenter(), 10));
    }

    private void addMarkers(GoogleMap googleMap, List<Club> clubList) {
        for (Club club : clubList) {
            for (GeoLocation geoLocation : club.getGeoLocations()) {
                LatLng latLng = new LatLng(geoLocation.getLat(), geoLocation.getLng());
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(club.getName()));
                marker.setTag(club.getName());
            }
        }
    }

    private LatLngBounds buildLatLngBounds(List<Club> clubList) {
        LatLngBounds.Builder latLngBoundsBuilder = LatLngBounds.builder();
        for (Club club : clubList) {
            for (GeoLocation geoLocation : club.getGeoLocations()) {
                LatLng latLng = new LatLng(geoLocation.getLat(), geoLocation.getLng());
                latLngBoundsBuilder.include(latLng);
            }
        }
        return latLngBoundsBuilder.build();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showList(List<Club> clubList) {
        this.clubList = clubList;
        getMapAsync(this);
    }

    @Override
    public void openClubDetail(String clubName) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String clubName = (String) marker.getTag();
        FragmentTransaction fragmentTransaction = getTabbedDelegate().getTabbedActivity(getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.replace(R.id.tabbed_content, DetailFragment.newInstance(ListableItemType.CLUB, clubName));
        fragmentTransaction.commit();
    }
}
