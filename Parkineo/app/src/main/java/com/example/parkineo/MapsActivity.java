package com.example.parkineo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkineo.activity.MenuLateralActivity;
import com.example.parkineo.databinding.FragmentMapaBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    //Variables
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    SearchView buscador;
    private FragmentMapaBinding binding;

    //Función que se ejecuta nada más iniciarse la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Establecemos la vista
        binding = FragmentMapaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.Google_Maps);

        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //Cuando esté el mapa puesto se ejecuta esta función
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Referenciamos el mapa
        mMap = googleMap;

        //Martín Zapatero
        LatLng martin = new LatLng(42.30345, -1.96315);
        mMap.addMarker(new MarkerOptions().position(martin).title("Parking Martín Zapatero"));

        //Arcca
        LatLng arcca = new LatLng(42.30512, -1.96112);
        mMap.addMarker(new MarkerOptions().position(arcca).title("Parking Arcca"));

        //Parkia
        LatLng parkia = new LatLng(42.46521, -2.45337);
        mMap.addMarker(new MarkerOptions().position(parkia).title("Parking Parkia"));

        //APK2
        LatLng apk2 = new LatLng(42.46329, -2.44545);
        mMap.addMarker(new MarkerOptions().position(apk2).title("Parking APK2"));

        //Cuando clickemos en alguno de los markers
        mMap.setOnMarkerClickListener(this);

        //Situamos la cámara en el parking de Martín Zapatero
        mMap.moveCamera(CameraUpdateFactory.newLatLng(martin));
    }

    //Función que se ejecuta cuando presionamos el botón de volver hacia atrás
    @Override
    public void onBackPressed() {
        //Cerramos el mapa
        finish();
    }

    //Función que se ejecuta cuando hacemos click en alguno de los markers
    @Override
    public boolean onMarkerClick(final Marker marker) {

        //Variables para guardar la información según el parking que pinchemos
        String informacionParking = "";
        String link_maps = "";

        //Comprobamos que parking hemos pulsado y añadimos una información u otra
        if(marker.getTitle().equals("Parking Martín Zapatero")) {
            informacionParking = "Nombre: Martín Zapatero" + System.getProperty ("line.separator") + "Ubicación: Calahorra" + System.getProperty ("line.separator") + "Link Google Maps: https://goo.gl/maps/Bn9kwzxxGdATkuq86" + System.getProperty ("line.separator") + "Número de plazas: 8";
            link_maps = "https://goo.gl/maps/Bn9kwzxxGdATkuq86";
        }
        else if(marker.getTitle().equals("Parking Arcca")) {
            informacionParking = "Nombre: Arcca" + System.getProperty ("line.separator") + "Ubicación: Calahorra" + System.getProperty ("line.separator") + "Link Google Maps: https://goo.gl/maps/1xCRSrXzzE2mYy3E6" + System.getProperty ("line.separator") + "Número de plazas: 8";
            link_maps = "https://goo.gl/maps/1xCRSrXzzE2mYy3E6";
        }
        else if(marker.getTitle().equals("Parking Parkia")) {
            informacionParking = "Nombre:Parkia" + System.getProperty ("line.separator") + "Ubicación: Logroño" + System.getProperty ("line.separator") + "Link Google Maps: https://goo.gl/maps/z2uA3zDaZfKs5hsx7" + System.getProperty ("line.separator") + "Número de plazas: 8";
            link_maps = "https://goo.gl/maps/z2uA3zDaZfKs5hsx7";
        }
        else if(marker.getTitle().equals("Parking APK2")) {
            informacionParking = "Nombre: APK2" + System.getProperty ("line.separator") + "Ubicación: Logroño" + System.getProperty ("line.separator") + "Link Google Maps: https://goo.gl/maps/Mx2rVg1D8wiVk9vJ9" + System.getProperty ("line.separator") + "Número de plazas: 8";
            link_maps = "https://goo.gl/maps/Mx2rVg1D8wiVk9vJ9";
        }

        //Creamos un builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        //Le ponemos la vista, mensaje y botón al builder
        String finalLink_maps = link_maps;
        builder.setView(inflater.inflate(R.layout.estilo_popup, null))
                .setPositiveButton("COPIAR LINK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked COPIAR LINK button
                        //Copiamos el link de Google Maps en el portapapeles
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("text", finalLink_maps);
                        clipboard.setPrimaryClip(clip);

                        //Mostramos mensaje indicando que se ha copiado correctamente
                        Toast.makeText(MapsActivity.this,"Link copiado correctamente",Toast. LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Cancelar button
                    }
                })
                .setMessage(informacionParking).setTitle("Información del parking");

        //Creamos el PopUp y lo mostramos
        AlertDialog dialog = builder.create();
        dialog.show();

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

}