package ch.lalumamesh.notenverwaltung.repository;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import ch.lalumamesh.notenverwaltung.Config;
import ch.lalumamesh.notenverwaltung.model.Pruefung;
import ch.lalumamesh.notenverwaltung.model.Semester;

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
                byte[] bytes = requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
                return bytes;
            }
        };
        queue.add(stringRequest);
    }
    public void loadPruefungen(Consumer<Pruefung[]> doWithPruefungen, Consumer<VolleyError> onError) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.url + "api/pruefung/",
                response -> {
                    Pruefung[] pruefungen = new Gson().fromJson(response, Pruefung[].class);
                    doWithPruefungen.accept(pruefungen);
                }, onError::accept);
        queue.add(stringRequest);
    }
}
