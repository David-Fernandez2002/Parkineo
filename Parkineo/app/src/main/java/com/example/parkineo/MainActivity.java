package com.example.parkineo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.parkineo.activity.MenuLateralActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Variables
    EditText etEmail, etContraseña;
    Button btnInicioSesion, btnRegistrarse;

    //Función que se ejecuta nada más iniciarse la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Cogemos los datos del SharedPreferences
        String logueado = leerDatosLogin("logueado");
        String usuario = leerDatosLogin("usuario");
        String email = leerDatosLogin("email");
        String contrasena = leerDatosLogin("contrasena");
        String fecha_nacimiento = leerDatosLogin("fecha_nacimiento");
        String sexo = leerDatosLogin("sexo");

        //Si vemos que no cierra sesión guardamos la sesión y abrimos directamente la aplicación sin iniciar sesión
        if(logueado.equals("Si")) {
            segundaPantalla(usuario, email, contrasena, fecha_nacimiento, sexo);
        }
        else {
            //Ponemos la pantalla de inicio de sesión
            setContentView(R.layout.activity_main);

            //Referenciamos los objetos
            etEmail = findViewById(R.id.email);
            etContraseña = findViewById(R.id.contrasena);
            btnInicioSesion = findViewById(R.id.btnIniciarSesion);
            btnRegistrarse = findViewById(R.id.btnRegistrarse);

            //Evento que lanza la otra actividad de registro cuando clickamos en el botón
            btnRegistrarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Abrimos la pantalla de registro
                    Intent intentRegistro = new Intent(MainActivity.this, RegisterActivity.class);
                    MainActivity.this.startActivityForResult(intentRegistro, 1);
                }
            });

            //Evento cuando hacemos click en el botón de iniciar sesión
            btnInicioSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Guardamos valores
                    final String email = etEmail.getText().toString();
                    final String contrasena = etContraseña.getText().toString();

                    Response.Listener<String> respuestaListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                //Guardamos que no está logueado
                                String logueado = "No";
                                JSONObject jsonRespuesta = new JSONObject(response);
                                boolean success = jsonRespuesta.getBoolean("success");

                                if(success) {

                                    //Obtenemos los datos
                                    logueado = "Si";
                                    String usuario = jsonRespuesta.getString("usuario");
                                    String fecha_nacimiento = jsonRespuesta.getString("fecha_nacimiento");
                                    String sexo = jsonRespuesta.getString("sexo");

                                    //Guardamos los datos
                                    guardarDatosLogin(logueado, usuario, email, contrasena, fecha_nacimiento, sexo);

                                    //Realizar el intent a la segunda pantalla
                                    segundaPantalla(usuario, email, contrasena, fecha_nacimiento, sexo);

                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("¡Alguno de los datos es incorrecto!").setNegativeButton("Reintentarlo", null).create().show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    //Realizamos la consulta
                    InicioSesionRequest inicioSesionRequest = new InicioSesionRequest(email, contrasena, respuestaListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(inicioSesionRequest);

                }
            });
        }

    }

    //Función que abre la segunda pantalla, la principal de la aplicación
    private void segundaPantalla(String usuario, String email, String contrasena, String fecha_nacimiento, String sexo) {

        //Cerramos la pantalla de inicio de sesión
        finish();

        //Realizamos el intent para abrir la pantalla
        Intent intent = new Intent(MainActivity.this, MenuLateralActivity.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("email", email);
        intent.putExtra("contrasena", contrasena);
        intent.putExtra("fecha_nacimiento", fecha_nacimiento);
        intent.putExtra("sexo", sexo);
        MainActivity.this.startActivity(intent);

    }

    //Función que guarda datos del SharedPreferences
    private void guardarDatosLogin(String logueado, String usuario, String email, String contrasena, String fecha_nacimiento, String sexo) {

        //Guardamos los datos
        SharedPreferences datos = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = datos.edit();
        editor.putString("logueado", logueado);
        editor.putString("usuario", usuario);
        editor.putString("email", email);
        editor.putString("contrasena", contrasena);
        editor.putString("fecha_nacimiento", fecha_nacimiento);
        editor.putString("sexo", sexo);
        editor.apply();
    }

    //Función que lee datos del SharedPreferences
    private String leerDatosLogin(String key) {
        //Leemos los datos
        SharedPreferences datos = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        return datos.getString(key, "");
    }

}