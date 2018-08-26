package de.heilsen.ganzhornfest.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
//TODO: handle no google play services
//TODO: handle outdated play servies
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    public static final String TAG = "MapFragment";
    private IsInBottomNavActivityFragmentDelegate isInBottomNavActivityFragmentDelegate;

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
        getTabbedDelegate().onViewCreated(view, savedInstanceState);
        getMapAsync(this);
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
        //ne: new LatLng(49.1961, 9.2301),
        //sw: new LatLng(49.1884, 9.2183));
        LatLngBounds ganzhornfestArea = new LatLngBounds(

                new LatLng( 49.190600, 9.221356 ),
                new LatLng( 49.191880, 9.225486 )
        );
        googleMap.setLatLngBoundsForCameraTarget(ganzhornfestArea);

        googleMap.setMinZoomPreference(17);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }


}
