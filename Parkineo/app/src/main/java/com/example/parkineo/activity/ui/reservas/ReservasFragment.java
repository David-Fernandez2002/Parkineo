package com.example.parkineo.activity.ui.reservas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkineo.MainActivity;
import com.example.parkineo.PlazaActivity;
import com.example.parkineo.R;
import com.example.parkineo.ReservarActivity;
import com.example.parkineo.databinding.FragmentReservasBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReservasFragment extends Fragment {

    //Variables
    private FragmentReservasBinding binding;
    Button btnMartin, btnArcca, btnParkia, btnAPK2;
    TextView tw2;
    Integer actualizarDatos = 0;

    //Función que se ejecuta cuando iniciamos la vista
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReservasViewModel reservasViewModel =
                new ViewModelProvider(this).get(ReservasViewModel.class);

        //Asignamos la vista
        binding = FragmentReservasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Referenciamos los objetos de la vista
        tw2 = binding.textView2;
        btnMartin = binding.btnMartin;
        btnArcca = binding.btnArcca;
        btnParkia = binding.btnParkia;
        btnAPK2 = binding.btnAPK2;

        //Volvemos a poner una plaza disponible en caso de que la reserva ya se haya pasado
        LiberarPlaza("Martín Zapatero", 1);
        LiberarPlaza("Arcca", 2);
        LiberarPlaza("Parkia", 3);
        LiberarPlaza("APK2", 4);

        //Ponemos las plazas ocupadas de las reservas actuales
        ActualizarDisponibilidad("Martín Zapatero", 1);
        ActualizarDisponibilidad("Arcca", 2);
        ActualizarDisponibilidad("Parkia", 3);
        ActualizarDisponibilidad("APK2", 4);

        btnMartin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PantallaPlaza("Martín Zapatero", 1);
            }
        });

        btnArcca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PantallaPlaza("Arcca", 2);
            }
        });

        btnParkia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PantallaPlaza("Parkia", 3);
            }
        });

        btnAPK2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PantallaPlaza("APK2", 4);
            }
        });

        return root;
    }

    //Función para actualizar a ocupadas las plazas que lo requieran
    public void ActualizarDisponibilidad(String parking, int id) {

        //Consultamos que reservas ya se han pasado
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getString(R.string.localhost) + "Consultar.php?parking=" + parking, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        //Obtenemos la plaza
                        jsonObject = response.getJSONObject(i);
                        String plaza = jsonObject.getString("plaza");

                        //Actualizamos las plazas de los resultados de la consulta anterior
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, getString(R.string.localhost) + "Actualizar.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(getActivity(), "OPERACIÓN EXITOSA", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                //Añadimos los parámetros a la consulta
                                Map<String,String> parametros=new HashMap<>();
                                parametros.put("id_parking", id+"");
                                parametros.put("nombre", plaza);
                                return  parametros;
                            }
                        };
                        //Realizamos la consulta
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(stringRequest);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        //Realizamos la consulta
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

    }

    //Función para actualizar a disponibles las plazas que lo requieran
    public void LiberarPlaza(String parking, int id) {

        //Consultamos
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( getString(R.string.localhost) + "Consultar2.php?parking=" + parking, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        //Obtenemos la plaza
                        jsonObject = response.getJSONObject(i);
                        String plaza = jsonObject.getString("plaza");

                        //Actualizamos las plazas de las reservas de la consulta anterior
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, getString(R.string.localhost) + "Actualizar2.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(getActivity(), "OPERACIÓN EXITOSA", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                //Añadimos los parámetros
                                Map<String,String> parametros=new HashMap<>();
                                parametros.put("id_parking", id+"");
                                parametros.put("nombre", plaza);
                                return  parametros;
                            }
                        };
                        //Realizamos la consulta
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(stringRequest);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        //Realizamos la consulta
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

    }

    //Función que abre la siguiente pantalla de las plazas del parking
    public void PantallaPlaza(String parking, Integer id) {
        Intent intent = new Intent(binding.getRoot().getContext(), PlazaActivity.class);
        intent.putExtra("parking", parking);
        intent.putExtra("id", id);
        binding.getRoot().getContext().startActivity(intent);
    }

    //Función que se ejecuta cuando quitamos la vista y la destruimos
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Función que se ejecuta cuando volvemos a la vista
    @Override
    public void onResume() {
        super.onResume();
        //Actualizar ventana
        if(actualizarDatos == 0) {
            actualizarDatos = 1;
        }
        else {
            Activity activity = (Activity)getContext();
            activity.recreate();
        }
    }

}