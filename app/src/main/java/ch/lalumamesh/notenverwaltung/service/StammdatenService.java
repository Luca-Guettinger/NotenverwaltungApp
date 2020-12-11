package ch.lalumamesh.notenverwaltung.service;

import com.android.volley.VolleyError;

import java.util.function.Consumer;

import ch.lalumamesh.notenverwaltung.model.Fach;
import ch.lalumamesh.notenverwaltung.model.Semester;
import ch.lalumamesh.notenverwaltung.repository.StammdatenRepository;

public class StammdatenService {
    StammdatenRepository stammdatenRepository;

    public StammdatenService(StammdatenRepository stammdatenRepository) {
        this.stammdatenRepository = stammdatenRepository;
    }

    public void loadSemester(Consumer<Semester[]> doWithSemesters, Consumer<VolleyError> onError) {
        stammdatenRepository.loadSemester(doWithSemesters, onError);
    }

    public void loadFach(Consumer<Fach[]> doWithFach, Consumer<VolleyError> onError) {
        stammdatenRepository.loadFach(doWithFach, onError);
    }
}
