package ch.lalumamesh.notenverwaltung.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.function.Consumer;

import ch.lalumamesh.notenverwaltung.Config;
import ch.lalumamesh.notenverwaltung.model.Fach;
import ch.lalumamesh.notenverwaltung.model.Semester;

public class StammdatenRepository {
    private final Context context;

    public StammdatenRepository(Context context) {
        this.context = context;
    }

    public void loadSemester(Consumer<Semester[]> doWithSemesters, Consumer<VolleyError> onError) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.url + "api/semester/",
                response -> {
                    Semester[] semesters = new Gson().fromJson(response, Semester[].class);
                    doWithSemesters.accept(semesters);
                }, onError::accept);
        queue.add(stringRequest);
    }

    public void loadFach(Consumer<Fach[]> doWithFach, Consumer<VolleyError> onError) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.url + "api/fach/",
                response -> {
                    Fach[] faecher = new Gson().fromJson(response, Fach[].class);
                    doWithFach.accept(faecher);
                }, onError::accept);
        queue.add(stringRequest);
    }
}
