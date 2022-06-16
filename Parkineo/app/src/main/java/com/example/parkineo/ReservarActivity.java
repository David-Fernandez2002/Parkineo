package com.example.parkineo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.parkineo.activity.MenuLateralActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservarActivity extends AppCompatActivity {

    //Variables
    String plaza, parking;
    Integer id_plaza;
    String fechaFormateada;
    String fechaFinal;
    EditText etTitular, etMatricula, etVehiculo, etDias, etFecha_reserva;
    Button btnReservar;
    RadioGroup rgSeguro;
    RadioButton rbSeleccionado;

    //Función que se ejecuta nada más iniciarse la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Establcemos la vista
        setContentView(R.layout.activity_reservar);

        //Cogemos los datos del intent
        Intent intent = getIntent();
        id_plaza = intent.getIntExtra("id_plaza", 0);
        plaza = intent.getStringExtra("plaza");
        parking = intent.getStringExtra("parking");

        //Variables de las opciones de registro
        etTitular = findViewById(R.id.usuario);
        etMatricula = findViewById(R.id.email);
        etVehiculo = findViewById(R.id.contrasena);
        etDias = findViewById(R.id.dias);
        etFecha_reserva = findViewById(R.id.fecha_nacimiento);
        rgSeguro = findViewById(R.id.rgSeguro);

        //Variable del botón para registrarse
        btnReservar = (Button) findViewById(R.id.btnRegistrarse);
        btnReservar.setOnClickListener(this::onClick);

        //Evento que abre el diálogo para escoger fecha
        etFecha_reserva.setOnClickListener(new View.OnClickListener() {
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

    //Función que se ejecuta cuando clickamos en el botón de Reservar
    public void onClick(View view) {

        //Cogemos los valores de los textos
        final String titular = etTitular.getText().toString();
        final String matricula = etMatricula.getText().toString();
        final String vehiculo = etVehiculo.getText().toString();
        Integer dias = Integer.parseInt(etDias.getText().toString());
        String fecha_reserva = etFecha_reserva.getText().toString();
        //Obtener el RadioButton seleccionado
        int IdSeleccionada = rgSeguro.getCheckedRadioButtonId();
        //Encontrar el RadioButton por la Id devuelta
        rbSeleccionado = (RadioButton) findViewById(IdSeleccionada);
        final String seguro = rbSeleccionado.getText().toString();
        String email = MenuLateralActivity.email;

        //Comprobamos si alguno de los campos está vacío
        if(etTitular.getText().toString().equals("") || etMatricula.getText().toString().equals("") || etDias.getText().toString().equals("") || etFecha_reserva.getText().toString().equals("")) {
            //Mostramos el error
            AlertDialog.Builder builder = new AlertDialog.Builder(ReservarActivity.this);
            builder.setMessage("¡No puedes dejar ningún campo vacío!").setNegativeButton("Aceptar", null).create().show();
        }
        //Comprobamos si los días que ha puesto son menor o igual que 0
        else if (dias <= 0) {
            //Mostramos el error
            AlertDialog.Builder builder = new AlertDialog.Builder(ReservarActivity.this);
            builder.setMessage("¡Los días de reserva tienen que ser mayor que 0!").setNegativeButton("Aceptar", null).create().show();
        }
        else {
            Response.Listener<String> respuesta = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success) {
                            //Indicamos que se ha realizado la reserva y cerramos la actividad
                            Toast.makeText(getApplicationContext(),"¡Reserva realizada con éxito!",Toast. LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            //Mostramos el error
                            AlertDialog.Builder builder = new AlertDialog.Builder(ReservarActivity.this);
                            builder.setMessage("¡Error en la reserva!").setNegativeButton("Reintentarlo", null).create().show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            //Comprobamos el valor de la BD y lo formateamos
            String valorBD = "F";
            if(seguro.equals("Sí")) {
                valorBD = "T";
            }
            else if(seguro.equals("No")) {
                valorBD = "F";
            }

            //Formateamos la fecha
            try {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(fechaFormateada));
                c.add(Calendar.DATE, dias);
                Date resultdate = new Date(c.getTimeInMillis());
                fechaFinal = sdf.format(resultdate);

            } catch (ParseException | java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Ejecutamos la consulta
            ReservaRequest reservaRequest = new ReservaRequest(titular, matricula, vehiculo, plaza, parking, dias, fechaFormateada, fechaFinal, valorBD, email, respuesta);
            RequestQueue queue = Volley.newRequestQueue(ReservarActivity.this);
            queue.add(reservaRequest);

        }
    }

    //Función del diálogo para escoger la fecha
    public static class SelectorFecha extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listenerFecha;

        public static ReservarActivity.SelectorFecha newInstance(DatePickerDialog.OnDateSetListener listenerFecha) {
            ReservarActivity.SelectorFecha fragmentoFecha = new ReservarActivity.SelectorFecha();
            fragmentoFecha.setListenerFecha(listenerFecha);
            return fragmentoFecha;
        }

        public void setListenerFecha(DatePickerDialog.OnDateSetListener listener) {
            this.listenerFecha = listener;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendario = Calendar.getInstance();
            int año = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), listenerFecha, año, mes, dia);
        }

    }

    private void mostrarSelectorFecha() {
        RegisterActivity.SelectorFecha newFragment = RegisterActivity.SelectorFecha.newInstance(new DatePickerDialog.OnDateSetListener() {
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

                    if (convertedDate.before(convertedDate2)) {
                        Toast.makeText(getApplicationContext(),"La fecha de reserva no puede ser anterior al día actual",Toast. LENGTH_SHORT).show();
                        etFecha_reserva.setText("");
                    }
                    else {
                        fechaFormateada = año + "-" + String.format("%02d", (mes+1)) + "-" + String.format("%02d", dia);
                        etFecha_reserva.setText(fechaSeleccionada);
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
