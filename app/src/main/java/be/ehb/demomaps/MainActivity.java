package be.ehb.demomaps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import be.ehb.demomaps.model.HoofdStadDAO;
import be.ehb.demomaps.model.Hoofdstad;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolygonClickListener, GoogleMap.OnMarkerClickListener {

    //magic numbers are bad mkay
    private final int REQUEST_LOCATION = 42;

    private final LatLng KINSHASA = new LatLng(-4.32758, 15.31357);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        supportMapFragment.getMapAsync(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frag_container, supportMapFragment)
                .commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnPolygonClickListener(this);
        map.setOnMarkerClickListener(this);

        setupCamera();
        addMarkers();
        startLocationUpdates();

    }


    private void setupCamera() {
        //update 1, show map
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(KINSHASA, 14);
        map.animateCamera(update);
        //update 2, show map, zoom in to see 3Dbuildings, change angle
        CameraPosition.Builder positionBuilder = new CameraPosition.Builder();
        CameraPosition position = positionBuilder.target(KINSHASA).zoom(18).tilt(60).build();
        CameraUpdate secondUpdate = CameraUpdateFactory.newCameraPosition(position);
        map.animateCamera(secondUpdate);
    }

    private void addMarkers() {
        map.addMarker(new MarkerOptions()
                .position(KINSHASA)
                .title("Kinshasa")
                .snippet("Hoofdstad van Congo")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        );

        map.addMarker(new MarkerOptions()
                .position(new LatLng(-4.3043777, 15.3100168))
                .title("Memling Resto")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_restaurant))
        );
        //marginale driehoek tekenen
        map.addPolygon(new PolygonOptions()
                        .add(new LatLng(50.810091, 4.934319), new LatLng(50.993622, 4.834812), new LatLng(50.986376, 5.050556))
                        .strokeColor(0xff000000)
                        .fillColor(0xff75315A)
                        .clickable(true)
                //ARGB
                //A = alpha -> doorzichtigheid, ff is vol en 00 is doorzichtig
        );

        //vanuit datasource verschillende steden continenten
        for (Hoofdstad stad : HoofdStadDAO.getInstance().getHoofdsteden()) {

            float hue = 0;

            switch (stad.getContinent()) {
                case EUROPA:
                    hue = BitmapDescriptorFactory.HUE_YELLOW;
                    break;
                case AFRIKA:
                    hue = BitmapDescriptorFactory.HUE_GREEN;
                    break;
                case OCEANIE:
                    hue = BitmapDescriptorFactory.HUE_VIOLET;
                    break;
            }

            map.addMarker(
                    new MarkerOptions()
                            .title(stad.getCityName())
                            .icon(BitmapDescriptorFactory.defaultMarker(hue))
                            .position(stad.getCoord())
            );

        }
    }

    private void startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions, REQUEST_LOCATION);

            } else {
                map.setMyLocationEnabled(true);
            }
        } else {
            map.setMyLocationEnabled(true);
        }
    }

    //android studio probeert slimmer te doen dan het is
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION) {
            for (int result : grantResults)
                if (result == PackageManager.PERMISSION_GRANTED)
                    map.setMyLocationEnabled(true);
        }

    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        Toast.makeText(getApplicationContext(), "Marginaaaaaal", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
        return false;
    }


}
