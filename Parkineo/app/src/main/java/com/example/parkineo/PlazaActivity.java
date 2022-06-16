package com.example.parkineo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkineo.activity.MenuLateralActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlazaActivity extends AppCompatActivity {

    //Variables
    public static String parking;
    public static  Integer id;
    String situacion;
    ImageView plazaA1, plazaA2, plazaB1, plazaB2, plazaC1, plazaC2, plazaD1, plazaD2;

    //Función que se ejecuta nada más iniciarse la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Establcemos la vista
        setContentView(R.layout.activity_plazas);

        //Cogemos los datos del intent
        Intent intent = getIntent();
        parking = intent.getStringExtra("parking");
        id = intent.getIntExtra("id", 0);

        //Referenciamos los objetos
        plazaA1 = findViewById(R.id.plazaA1);
        plazaA2 = findViewById(R.id.plazaA2);
        plazaB1 = findViewById(R.id.plazaB1);
        plazaB2 = findViewById(R.id.plazaB2);
        plazaC1 = findViewById(R.id.plazaC1);
        plazaC2 = findViewById(R.id.plazaC2);
        plazaD1 = findViewById(R.id.plazaD1);
        plazaD2 = findViewById(R.id.plazaD2);

        //URL del WebService
        String URL = getString(R.string.localhost) + "Plazas.php?id_parking=" + id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                //Guardamos las imágenes
                Integer imagen_rojo = R.drawable.coche_aparcado_rojo;
                Integer imagen_verde = R.drawable.coche_aparcado_verde;

                //Bucle para recorrer los resultados de la consulta
                for (int i = 0; i < response.length(); i++) {
                    try {

                        //Cogemos los datos
                        jsonObject = response.getJSONObject(i);
                        Integer id_plaza = jsonObject.getInt("id_plaza");
                        String nombre = jsonObject.getString("nombre");
                        String disponibilidad = jsonObject.getString("disponibilidad");

                        //Miramos que plaza es
                        switch (nombre) {
                            case "A1":
                                if(disponibilidad.equals("T")) {
                                    situacion = "Libre";
                                    plazaA1.setImageResource(imagen_verde);
                                }
                                else if(disponibilidad.equals("F")) {
                                    situacion = "Ocupada";
                                    plazaA1.setImageResource(imagen_rojo);
                                }

                                ArrayList<String> miArreglo = new ArrayList<String>();
                                miArreglo.add(id_plaza.toString());
                                miArreglo.add(nombre);
                                miArreglo.add(situacion);

                                plazaA1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        infoPlaza(Integer.parseInt(miArreglo.get(0)), parking, miArreglo.get(1), miArreglo.get(2));
                                    }
                                });

                                break;
                            case "A2":
                                if(disponibilidad.equals("T")) {
                                    situacion = "Libre";
                                    plazaA2.setImageResource(imagen_verde);
                                }
                                else if(disponibilidad.equals("F")) {
                                    situacion = "Ocupada";
                                    plazaA2.setImageResource(imagen_rojo);
                                }

                                ArrayList<String> miArreglo1 = new ArrayList<String>();
                                miArreglo1.add(id_plaza.toString());
                                miArreglo1.add(nombre);
                                miArreglo1.add(situacion);

                                plazaA2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        infoPlaza(Integer.parseInt(miArreglo1.get(0)), parking, miArreglo1.get(1), miArreglo1.get(2));
                                    }
                                });

                                break;
                            case "B1":
                                if(disponibilidad.equals("T")) {
                                    situacion = "Libre";
                                    plazaB1.setImageResource(imagen_verde);
                                }
                                else if(disponibilidad.equals("F")) {
                                    situacion = "Ocupada";
                                    plazaB1.setImageResource(imagen_rojo);
                                }

                                ArrayList<String> miArreglo2 = new ArrayList<String>();
                                miArreglo2.add(id_plaza.toString());
                                miArreglo2.add(nombre);
                                miArreglo2.add(situacion);

                                plazaB1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        infoPlaza(Integer.parseInt(miArreglo2.get(0)), parking, miArreglo2.get(1), miArreglo2.get(2));
                                    }
                                });

                                break;
                            case "B2":
                                if(disponibilidad.equals("T")) {
                                    situacion = "Libre";
                                    plazaB2.setImageResource(imagen_verde);
                                }
                                else if(disponibilidad.equals("F")) {
                                    situacion = "Ocupada";
                                    plazaB2.setImageResource(imagen_rojo);
                                }

                                ArrayList<String> miArreglo3 = new ArrayList<String>();
                                miArreglo3.add(id_plaza.toString());
                                miArreglo3.add(nombre);
                                miArreglo3.add(situacion);

                                plazaB2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        infoPlaza(Integer.parseInt(miArreglo3.get(0)), parking, miArreglo3.get(1), miArreglo3.get(2));
                                    }
                                });

                                break;
                            case "C1":
                                if(disponibilidad.equals("T")) {
                                    situacion = "Libre";
                                    plazaC1.setImageResource(imagen_verde);
                                }
                                else if(disponibilidad.equals("F")) {
                                    situacion = "Ocupada";
                                    plazaC1.setImageResource(imagen_rojo);
                                }

                                ArrayList<String> miArreglo4 = new ArrayList<String>();
                                miArreglo4.add(id_plaza.toString());
                                miArreglo4.add(nombre);
                                miArreglo4.add(situacion);

                                plazaC1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        infoPlaza(Integer.parseInt(miArreglo4.get(0)), parking, miArreglo4.get(1), miArreglo4.get(2));
                                    }
                                });

                                break;
                            case "C2":
                                if(disponibilidad.equals("T")) {
                                    situacion = "Libre";
                                    plazaC2.setImageResource(imagen_verde);
                                }
                                else if(disponibilidad.equals("F")) {
                                    situacion = "Ocupada";
                                    plazaC2.setImageResource(imagen_rojo);
                                }

                                ArrayList<String> miArreglo5 = new ArrayList<String>();
                                miArreglo5.add(id_plaza.toString());
                                miArreglo5.add(nombre);
                                miArreglo5.add(situacion);

                                plazaC2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        infoPlaza(Integer.parseInt(miArreglo5.get(0)), parking, miArreglo5.get(1), miArreglo5.get(2));
                                    }
                                });

                                break;
                            case "D1":
                                if(disponibilidad.equals("T")) {
                                    situacion = "Libre";
                                    plazaD1.setImageResource(imagen_verde);
                                }
                                else if(disponibilidad.equals("F")) {
                                    situacion = "Ocupada";
                                    plazaD1.setImageResource(imagen_rojo);
                                }

                                ArrayList<String> miArreglo6 = new ArrayList<String>();
                                miArreglo6.add(id_plaza.toString());
                                miArreglo6.add(nombre);
                                miArreglo6.add(situacion);

                                plazaD1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        infoPlaza(Integer.parseInt(miArreglo6.get(0)), parking, miArreglo6.get(1), miArreglo6.get(2));
                                    }
                                });

                                break;
                            case "D2":
                                if(disponibilidad.equals("T")) {
                                    situacion = "Libre";
                                    plazaD2.setImageResource(imagen_verde);
                                }
                                else if(disponibilidad.equals("F")) {
                                    situacion = "Ocupada";
                                    plazaD2.setImageResource(imagen_rojo);
                                }

                                ArrayList<String> miArreglo7 = new ArrayList<String>();
                                miArreglo7.add(id_plaza.toString());
                                miArreglo7.add(nombre);
                                miArreglo7.add(situacion);

                                plazaD2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        infoPlaza(Integer.parseInt(miArreglo7.get(0)), parking, miArreglo7.get(1), miArreglo7.get(2));
                                    }
                                });

                                break;
                        }

                    } catch (JSONException e) {
                        Toast.makeText(PlazaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlazaActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
        );
        //Añadimos la consulta
        RequestQueue requestQueue = Volley.newRequestQueue(PlazaActivity.this);
        requestQueue.add(jsonArrayRequest);

    }

    //Función que nos muestra un diálogo con la información de la plaza
    public void infoPlaza(Integer id_plaza, String parking, String nombre, String situacion) {

        //Variable que contiene la información
        String info = "Nombre del parking: " + parking + System.getProperty ("line.separator") + "Nombre plaza: " + nombre + System.getProperty ("line.separator") + "Disponibilidad: " + situacion + System.getProperty ("line.separator");

        //Creamos un builder
        AlertDialog.Builder builder = new AlertDialog.Builder(PlazaActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        //Le ponemos la vista, mensaje y botón al builder
        builder.setView(inflater.inflate(R.layout.estilo_popup, null))
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        //Evaluamos si la plaza está libre u ocupada
                        if(situacion.equals("Libre")) {
                            //Abrimos la pantalla para reservar
                            Intent intent = new Intent(PlazaActivity.this, ReservarActivity.class);
                            intent.putExtra("id_plaza", id_plaza);
                            intent.putExtra("plaza", nombre);
                            intent.putExtra("parking", parking);
                            PlazaActivity.this.startActivity(intent);
                            finish();

                        }
                        else if(situacion.equals("Ocupada")) {
                            Toast.makeText(PlazaActivity.this,"No puedas reservar esta plaza, está ocupada",Toast. LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Cancelar button
                    }
                })
                .setMessage(info).setTitle("Información de la plaza");

        //Creamos el PopUp y lo mostramos
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
