package com.example.parkineo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InicioSesionRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://192.168.96.175/Parkineo/Inicio_sesion.php";
    private Map<String, String> params;
    public InicioSesionRequest(String email, String contrasena, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("contrasena", contrasena);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
