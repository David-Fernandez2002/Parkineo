package com.example.parkineo.activity.ui.info_reservas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.example.parkineo.R;
import com.example.parkineo.activity.MenuLateralActivity;
import com.example.parkineo.databinding.FragmentInformacionReservasBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoReservasFragment extends Fragment {

    //Variables
    private FragmentInformacionReservasBinding binding;
    ArrayList<String> id_reservas = new ArrayList<String>();
    Integer id_parking = 0;
    String nombre_plaza = "";

    //Función que se ejecuta nada más crearse la vista
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InfoReservasViewModel informacion_reservas_ViewModel =
                new ViewModelProvider(this).get(InfoReservasViewModel.class);

        //Asignamos la vista
        binding = FragmentInformacionReservasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Llamamos a la función que muestra las reservas del usuario
        buscarReservas(getString(R.string.localhost) + "Info_Reservas.php?email=" + MenuLateralActivity.email);

        return root;

    }

    //Función que se ejecuta cuando salimos de la vista y la destruimos
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Función que utiliza un WebService para obtener las reservas del usuario y mostrarlas en la vista
    private void buscarReservas(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                //Bucle para obtener los datos de los resultados de la consulta
                for (int i = 0; i < response.length(); i++) {
                    try {

                        //Obtenemos la posición del bucle
                        jsonObject = response.getJSONObject(i);

                        //Obtenemos el linear layout donde colocar los botones
                        LinearLayout llBotonera = (LinearLayout) binding.llBotonera;

                        //Creamos las propiedades de layout que tendrán los botones.
                        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
                        LinearLayout.LayoutParams layoutText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT );
                        LinearLayout.LayoutParams layoutBoton = new LinearLayout.LayoutParams(230, 150);

                        //Id de la reserva y establecemos valor del seguro
                        Integer id_reserva = i+1;
                        String tieneSeguro = "No";

                        //Comprobamos el valor de la consulta y lo formateamos
                        if(jsonObject.getString("seguro").equals("T")) {
                            tieneSeguro = "Sí";
                        } else {
                            tieneSeguro = "No";
                        }

                        //Variable en la que guardamos la información de cada reserva y añadimos los ID de las reservas a un array
                        String informacion = "Id de la reserva: " + jsonObject.getString("id_reserva") + System.getProperty ("line.separator") + "Titular: " + jsonObject.getString("titular") + System.getProperty ("line.separator") + "Matrícula: " + jsonObject.getString("matricula") + System.getProperty ("line.separator") + "Vehículo: " + jsonObject.getString("vehiculo") + System.getProperty ("line.separator") + "Plaza: " + jsonObject.getString("plaza") + System.getProperty ("line.separator") + "Parking: " + jsonObject.getString("parking") + System.getProperty ("line.separator") + "Días: " + jsonObject.getString("dias") + System.getProperty ("line.separator") + "Fecha de la reserva: " + jsonObject.getString("fecha_reserva") + System.getProperty ("line.separator") + "Fecha final reserva: " + jsonObject.getString("fecha_final") + System.getProperty ("line.separator") + "Seguro: " + tieneSeguro + System.getProperty ("line.separator");
                        id_reservas.add(jsonObject.getString("id_reserva"));

                        //Guardamos el nombre de la plaza
                        nombre_plaza = jsonObject.getString("plaza");

                        //Comprobamos el nombre del parking y asignamos su ID
                        if(jsonObject.getString("parking").equals("Martín Zapatero")) {
                            id_parking = 1;
                        }
                        else if(jsonObject.getString("parking").equals("Arcca")) {
                            id_parking = 2;
                        }
                        else if(jsonObject.getString("parking").equals("Parkia")) {
                            id_parking = 3;
                        }
                        else if(jsonObject.getString("parking").equals("APK2")) {
                            id_parking = 4;
                        }

                        //Creamos el texto
                        EditText text = new EditText(getContext());
                        text.setTextColor(Color.BLACK);
                        text.setKeyListener(null);
                        text.setLayoutParams(layoutText);
                        text.setText("Reserva: " + id_reserva);

                        //Creamos el layout
                        LinearLayout linear = new LinearLayout(getContext());
                        linear.setLayoutParams(lp);

                        //Creamos el botón para eliminar la reserva
                        Button botonEliminar = new Button(getContext());
                        botonEliminar.setBackgroundResource(R.drawable.boton_informacion);
                        //Asignamos propiedades de layout al boton
                        botonEliminar.setLayoutParams(layoutBoton);
                        //Asignamos Texto al botón
                        botonEliminar.setText("ELIMINAR");
                        botonEliminar.setTextSize(12);

                        //Creamos el botón para ver la reserva
                        Button botonVer = new Button(getContext());
                        botonVer.setBackgroundResource(R.drawable.boton_informacion);
                        //Asignamos propiedades de layout al boton
                        botonVer.setLayoutParams(layoutBoton);
                        //Asignamos Texto al botón
                        botonVer.setText("VER");
                        botonVer.setTextSize(12);

                        //Cuando clickemos en el botón ver
                        botonVer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //Creamos un builder
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = requireActivity().getLayoutInflater();

                                //Le ponemos la vista, mensaje y botón al builder
                                builder.setView(inflater.inflate(R.layout.estilo_popup, null))
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User clicked OK button
                                        }
                                    }).setMessage(informacion).setTitle("Reserva " + id_reserva);

                                //Creamos el PopUp y lo mostramos
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });

                        //Cuando clickemos en el botón eliminar
                        botonEliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Creamos un builder
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Eliminar reserva");
                                builder.setMessage("¿Estás seguro de que quieres eliminar este registro?");

                                //Le ponemos la vista, mensaje y botón al builder
                                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //Actualizamos la disponibilidad de la plaza
                                        StringRequest actualizarRequest=new StringRequest(Request.Method.POST, getString(R.string.localhost) + "Actualizar2.php", new Response.Listener<String>() {
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
                                                Map<String,String> parametros=new HashMap<>();

                                                //Añadimos los parámetros a la consulta
                                                parametros.put("id_parking", id_parking+"");
                                                parametros.put("nombre", nombre_plaza);
                                                return  parametros;
                                            }
                                        };
                                        //Realizamos la consulta
                                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                        requestQueue.add(actualizarRequest);

                                        //Eliminamos la reserva
                                        StringRequest eliminarRequest=new StringRequest(Request.Method.POST, getString(R.string.localhost) + "EliminarReserva.php", new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                //Informamos que se ha eliminado y recargamos la actividad
                                                Toast.makeText(getActivity(), "Reserva eliminada correctamente", Toast.LENGTH_SHORT).show();
                                                Activity activity = (Activity)getContext();
                                                activity.recreate();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                //Añadimos parámetros para eliminar la reserva
                                                Map<String,String> parametros=new HashMap<>();
                                                parametros.put("id_reserva", Integer.parseInt(id_reservas.get(id_reserva-1))+"");
                                                return  parametros;
                                            }
                                        };
                                        //Ejecutamos consulta
                                        RequestQueue request = Volley.newRequestQueue(getActivity());
                                        request.add(eliminarRequest);

                                    }
                                })
                                .setNegativeButton("Cancelar", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });

                        //Añadimos los botones al layout
                        linear.addView(text);
                        linear.addView(botonEliminar);
                        linear.addView(botonVer);

                        //Creamos el CardView
                        CardView cardview = new CardView(getContext());
                        cardview.setLayoutParams(lp);

                        //Añadimos las propiedades del CardView
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) cardview.getLayoutParams();
                        layoutParams.setMargins(90, 100, 90, 100);
                        cardview.requestLayout();
                        cardview.setCardBackgroundColor(Color.BLUE);
                        cardview.setRadius(150);
                        cardview.addView(linear);

                        //Añadimos el CardView al layout
                        llBotonera.addView(cardview);

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Referenciamos los objetos del layout
                Space espacio1 = binding.espacio1;
                Space espacio2 = binding.espacio2;
                TextView textoNoreserva = binding.textoNoreserva;
                ImageView imagenReserva = binding.imagenReserva;

                //Mostramos los elementos cuando no hay registros de reservas
                espacio1.setVisibility(View.VISIBLE);
                espacio2.setVisibility(View.VISIBLE);
                textoNoreserva.setVisibility(View.VISIBLE);
                imagenReserva.setVisibility(View.VISIBLE);

            }
        }
        );
        //Realizamos la consulta
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

}