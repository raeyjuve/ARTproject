package Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pgd.dev.artproject.R;

/**
 * Created by Frog-Grammar on 24-Feb-16.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback{
    public MapsFragment() {}


    private SupportMapFragment fragment;
    private GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fragmentManager = getChildFragmentManager();
        fragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.map_container, fragment).commit();
        }
        fragment.getMapAsync(this);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (map == null) {
//            map = fragment.getMap();
////            map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
//
//            LatLng latLng = new LatLng(-6.1902144, 106.8466695);
//
//            // create marker
//            // Changing marker icon
//            // adding marker
//            MarkerOptions marker = new MarkerOptions().position(latLng).title("Kantor Pusat");
//            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(20).build();
//
//            map.addMarker(marker);
//            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            map.getUiSettings().setAllGesturesEnabled(true);
//            map.getUiSettings().setCompassEnabled(true);
//            map.getUiSettings().setZoomControlsEnabled(true);
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng kppp = new LatLng(-6.1902144, 106.8466695);

        // create marker
        // Changing marker icon
        // adding marker
        MarkerOptions marker = new MarkerOptions();
        marker.position(kppp);
        marker.title("Kantor Pusat");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        map.addMarker(marker);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(kppp).zoom(17).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        map.moveCamera(CameraUpdateFactory.newLatLng(kppp));
    }
}
