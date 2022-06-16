package com.example.parkineo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables
    EditText etUsuario, etEmail, etContrasena, etFecha_nacimiento;
    Button btnRegistrarse;
    RadioGroup rgSexo;
    RadioButton rbSeleccionado;

    //Función que se ejecuta nada más iniciarse la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Establcemos la vista
        setContentView(R.layout.activity_registro);

        //Referenciamos los objetos
        etUsuario = findViewById(R.id.usuario);
        etEmail = findViewById(R.id.email);
        etContrasena = findViewById(R.id.contrasena);
        etFecha_nacimiento = findViewById(R.id.fecha_nacimiento);
        rgSexo = findViewById(R.id.rgSeguro);

        //Variable del botón para registrarse
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(this);

        //Evento que abre el diálogo para escoger fecha
        etFecha_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fecha_nacimiento:
                        mostrarSelectorFecha();
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        //Cogemos los valores de los objetos
        final String usuario = etUsuario.getText().toString();
        final String email = etEmail.getText().toString();
        final String contrasena = etContrasena.getText().toString();
        final String fecha_nacimiento = etFecha_nacimiento.getText().toString();

        //Formato del campo email
        String textoEmail = email.trim();
        String patronEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        //Comprobamos si el valor del campo coincide con el patrón
        if (!textoEmail.matches(patronEmail)) {
            etEmail.setError("Introduce un formato válido");
        }

        //Si la longitud de la contraseña es menor que 6
        if(contrasena.length() < 6){
            //Informamos del error
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("La contraseña debe ser de mínimo 6 caracteres").setNegativeButton("Aceptar", null).create().show();
            return;
        }

        //Obtener el RadioButton seleccionado
        int IdSeleccionada = rgSexo.getCheckedRadioButtonId();
        //Encontrar el RadioButton por la Id devuelta
        rbSeleccionado = (RadioButton) findViewById(IdSeleccionada);
        final String sexo = rbSeleccionado.getText().toString();

        //Comprobamos si alguno de los campos está vacío
        if(usuario.equals("") || email.equals("") || contrasena.equals("") || fecha_nacimiento.equals("")) {
            //Informamos
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("¡No puedes dejar ningún campo vacío!").setNegativeButton("Aceptar", null).create().show();
        }
        else {

            //Ejecutamos la inserción del usuario
            Response.Listener<String> respuesta = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success) {
                            //Cerramos actividad e informamos que se ha registrado
                            finish();
                            Toast.makeText(RegisterActivity.this,"¡Usuario registrado correctamente¡",Toast. LENGTH_SHORT).show();

                        }
                        else {
                            //Error
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("¡Error en el registro!").setNegativeButton("Reintentarlo", null).create().show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            //Ejecutamos la consulta
            RegisterRequest registroRequest = new RegisterRequest(usuario, email, contrasena, fecha_nacimiento, sexo, respuesta);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registroRequest);
        }
    }

    //Cuadro de diálogo de la fecha
    public static class SelectorFecha extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static SelectorFecha newInstance(DatePickerDialog.OnDateSetListener listener) {
            SelectorFecha fragmento = new SelectorFecha();
            fragmento.setListener(listener);
            return fragmento;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendario = Calendar.getInstance();
            int año = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), listener, año, mes, dia);
        }

    }

    private void mostrarSelectorFecha() {
        SelectorFecha newFragment = SelectorFecha.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
                // +1 porque Enero es 0
                String fechaSeleccionada = String.format("%02d", dia) + "/" + String.format("%02d", (mes+1)) + "/" + año;
                String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date convertedDate = new Date();
                Date convertedDate2 = new Date();

                try {
                    convertedDate = dateFormat.parse(fechaSeleccionada);
                    convertedDate2 = dateFormat.parse(fechaActual);

                    if (convertedDate.after(convertedDate2)) {
                        Toast.makeText(getApplicationContext(),"Fecha escogida mayor que la actual",Toast. LENGTH_SHORT).show();
                        etFecha_nacimiento.setText("");
                    }
                    else {
                        etFecha_nacimiento.setText(fechaSeleccionada);
                    }

                } catch (ParseException | java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        newFragment.show(getSupportFragmentManager(), "DialogoFecha");
    }

}