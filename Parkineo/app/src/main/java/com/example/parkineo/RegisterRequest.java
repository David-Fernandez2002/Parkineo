package com.example.parkineo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL= "http://192.168.96.175/Parkineo/Registro.php";
    private Map<String, String> params;
    public RegisterRequest(String usuario, String email, String contrasena, String fecha_nacimiento, String sexo, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuario", usuario);
        params.put("email", email);
        params.put("contrasena", contrasena);
        params.put("fecha_nacimiento", fecha_nacimiento);
        params.put("sexo", sexo);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
