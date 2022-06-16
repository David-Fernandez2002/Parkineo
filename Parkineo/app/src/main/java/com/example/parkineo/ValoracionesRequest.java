package com.example.parkineo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValoracionesRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL="http://192.168.96.175/Parkineo/Valoraciones.php";
    private Map<String, String> params;
    public ValoracionesRequest(Float estrellas, String comentario, String email, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("estrellas", estrellas+"");
        params.put("comentario", comentario);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
