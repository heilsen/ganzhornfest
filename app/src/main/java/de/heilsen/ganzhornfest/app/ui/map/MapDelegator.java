package de.heilsen.ganzhornfest.app.ui.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MapDelegator implements OnMapReadyCallback {
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
