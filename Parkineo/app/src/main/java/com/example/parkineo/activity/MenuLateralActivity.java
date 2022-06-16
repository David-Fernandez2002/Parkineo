package com.example.parkineo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.parkineo.MainActivity;
import com.example.parkineo.MapsActivity;
import com.example.parkineo.PlazaActivity;
import com.example.parkineo.R;
import com.example.parkineo.RegisterActivity;
import com.example.parkineo.RegisterRequest;
import com.example.parkineo.ReservarActivity;
import com.example.parkineo.ValoracionesRequest;
import com.example.parkineo.databinding.ActivityMenuLateralBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import mehdi.sakout.aboutpage.Element;
import mehdi.sakout.aboutpage.AboutPage;


public class MenuLateralActivity extends AppCompatActivity {

    //Variables
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuLateralBinding binding;
    NavigationView navigationView;
    Boolean isClose = true;
    AlertDialog dialog;
    private static final int SELECT_FILE = 1;
    public static String email;
    String usuario, contrasena, fecha_nacimiento, sexo;

    //Función que se ejecuta nada más iniciarse la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Cogemos los datos que se nos han pasado de la actividad anterior
        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        email = intent.getStringExtra("email");
        contrasena = intent.getStringExtra("contrasena");
        fecha_nacimiento = intent.getStringExtra("fecha_nacimiento");
        sexo = intent.getStringExtra("sexo");

        //Establecemos la vista
        binding = ActivityMenuLateralBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Asignamos la funcionalidad de gmail al botón del correo
        setSupportActionBar(binding.appBarMenuLateral.toolbar);
        binding.appBarMenuLateral.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contacto por email", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Abrimos la aplicación de Gmail para mandarnos un correo
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "davidcalahorra2002@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

        //Cogemos los items de la barra lateral
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_reservas, R.id.nav_mapa, R.id.nav_informacion_reservas)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_lateral);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Información barra lateral
        View headerView = navigationView.getHeaderView(0);
        TextView Textusuario = (TextView) headerView.findViewById(R.id.Usuario);
        Textusuario.setText(usuario);
        TextView Textemail = (TextView) headerView.findViewById(R.id.Email);
        Textemail.setText(email);

        //Menú de compartir la app por redes sociales
        Menu footer = navigationView.getMenu();
        MenuItem compartir = (MenuItem) footer.findItem(R.id.nav_compartir);
        compartir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent compartir = new Intent(Intent.ACTION_SEND);
                compartir.setType("text/plain");
                String mensaje = "https://github.com/David-Fernandez2002/Parkineo";
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Parkineo");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(compartir, "Compartir vía"));
                return false;
            }
        });

        //Abrimos el diálogo de valoración
        MenuItem valorar = (MenuItem) footer.findItem(R.id.nav_valorar);
        valorar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Creamos un builder
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuLateralActivity.this);
                LayoutInflater inflater = getLayoutInflater();

                //Le ponemos la vista, mensaje y botón al builder
                builder.setView(inflater.inflate(R.layout.estilo_popup2, null))
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Aceptar();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Cancelar button
                            }
                        });

                //Creamos el PopUp y lo mostramos
                dialog = builder.create();
                dialog.show();
                return false;
            }
        });

        //Abrimos la pantalla de información sobre nosotros
        MenuItem sobre_nosotros = (MenuItem) footer.findItem(R.id.nav_sobre_nosotros);
        sobre_nosotros.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Element adsElement = new Element();
                adsElement.setTitle("Anúnciate con nosotros");

                //Añadimos toda la información a la página
                View aboutPage = new AboutPage(MenuLateralActivity.this)
                        .isRTL(false)
                        .setImage(R.drawable.parkineo)
                        .setDescription("Esta es una aplicación desarrollada por David Fernández Pérez, la cual ha sido creada con el objetivo de ayudar y hacer más sencillo aparcar, esperamos que os guste la aplicación tanto como a mí me ha gustado hacerla, por eso es de gran ayuda que nos puntúes y sigas en nuestras redes sociales para hacer que cada vez llegue a más gente la app, gracias y ¡Disfruta!")
                        .addItem(new Element().setTitle("Version 1.0"))
                        .addItem(adsElement)
                        .addGroup("Conecta con nosotros")
                        .addEmail("davidcalahorra2002@gmail.com")
                        .addWebsite("https://github.com/David-Fernandez2002/Parkineo")
                        .addTwitter("david_fe02")
                        .addYoutube("UCeq71rRWnXFrtvCl4FUelVQ")
                        .addGitHub("David-Fernandez2002/Parkineo")
                        .addInstagram("david._fernandez")
                        .addItem(getCopyRight())
                        .create();
                setContentView(aboutPage);
                isClose = false;
                return false;
            }
        });

    }

    //Función que abre el mapa
    public void abrirMapa(View v){
        //Abrimos el mapa
        Intent intent = new Intent(MenuLateralActivity.this, MapsActivity.class);
        MenuLateralActivity.this.startActivity(intent);
    }

    //Establecemos las funciones del CopyRight
    Element getCopyRight() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format("Copyright 2022 Parkineo, Todos los derechos reservados", Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.copyright);
        copyRightsElement.setAutoApplyIconTint(true);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.holo_blue_bright);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuLateralActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    //Función que se ejecuta cuando pulsamos aceptar en el diálogo de las valorciones
    public void Aceptar() {
        //Hacemos referencia a los objetos
        RatingBar rating = dialog.findViewById(R.id.ratingBar);
        EditText text = dialog.findViewById(R.id.comentario);

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success) {
                        Toast.makeText(getApplicationContext(),"Valoración enviada correctamente",Toast. LENGTH_SHORT).show();
                    }
                    else {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MenuLateralActivity.this);
                        builder.setMessage("¡No se ha podido enviar tu valoración!").setNegativeButton("Aceptar", null).create().show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //Ejecutamos la consulta
        ValoracionesRequest valoracionRequest = new ValoracionesRequest(rating.getRating(), text.getText().toString(), email, respuesta);
        RequestQueue queue = Volley.newRequestQueue(MenuLateralActivity.this);
        queue.add(valoracionRequest);

    }

    //Función que se ejecuta cuando pulsamos el botón de volver hacia atrás
    @Override
    public void onBackPressed() {
        if (isClose){
            finish();
        } else {
            setContentView(binding.getRoot());
            isClose = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lateral, menu);

        return true;
    }

    //Función que capturamos los elementos de la barra
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itemCerrarSesion:
                finish();

                //Guardamos estos datos
                SharedPreferences datos = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = datos.edit();
                editor.putString("logueado", "No");
                editor.putString("usuario", usuario);
                editor.putString("email", email);
                editor.putString("contrasena", contrasena);
                editor.putString("fecha_nacimiento", fecha_nacimiento);
                editor.putString("sexo", sexo);
                editor.apply();

                //Abrimos la pantalla principal
                Intent intent = new Intent(MenuLateralActivity.this, MainActivity.class);
                MenuLateralActivity.this.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_lateral);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
}