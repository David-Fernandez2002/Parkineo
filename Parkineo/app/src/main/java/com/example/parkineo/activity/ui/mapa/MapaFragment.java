package com.example.parkineo.activity.ui.mapa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.parkineo.MapsActivity;
import com.example.parkineo.PlazaActivity;
import com.example.parkineo.ReservarActivity;
import com.example.parkineo.databinding.AbrirMapaBinding;
import com.example.parkineo.databinding.ActivityMenuLateralBinding;
import com.example.parkineo.databinding.FragmentMapaBinding;
import com.example.parkineo.databinding.FragmentReservasBinding;

public class MapaFragment extends Fragment {

    //Variables
    private AbrirMapaBinding binding;

    //Función que se ejecuta cuando iniciamos la vista
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapaViewModel mapaViewModel =
                new ViewModelProvider(this).get(MapaViewModel.class);

        //Asignamos la vista
        binding = AbrirMapaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    //Función que se ejecuta cuando quitamos la vista y la destruimos
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}