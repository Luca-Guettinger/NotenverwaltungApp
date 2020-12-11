package ch.lalumamesh.notenverwaltung.repository;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import ch.lalumamesh.notenverwaltung.Config;
import ch.lalumamesh.notenverwaltung.model.Pruefung;

public class PruefungenRepository {
    private final Context context;

    public PruefungenRepository(Context context) {
        this.context = context;
    }

    public void savePruefung(Pruefung pruefung, Consumer<String> onResponse, Consumer<VolleyError> onError, Consumer<Exception> onBodyException) {
        String requestBody = new Gson().toJson(pruefung);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.url + "api/pruefung/",
                onResponse::accept,
                onError::accept) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    byte[] bytes = requestBody == null ? null : requestBody.getBytes("utf-8");
                    return bytes;
                } catch (UnsupportedEncodingException uee) {
                    onBodyException.accept(uee);
                    return null;
                }
            }
        };
        queue.add(stringRequest);
    }
}
