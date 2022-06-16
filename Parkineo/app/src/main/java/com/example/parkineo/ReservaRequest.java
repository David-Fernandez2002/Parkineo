package com.example.parkineo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReservaRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL="http://192.168.96.175/Parkineo/Reserva.php";
    private Map<String, String> params;
    public ReservaRequest(String titular, String matricula, String vehiculo, String plaza, String parking, Integer dias, String fecha_reserva, String fecha_final, String seguro, String email, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("titular", titular);
        params.put("matricula", matricula);
        params.put("vehiculo", vehiculo);
        params.put("plaza", plaza);
        params.put("parking", parking);
        params.put("dias",dias+"");
        params.put("fecha_reserva", fecha_reserva);
        params.put("fecha_final", fecha_final);
        params.put("seguro", seguro);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
