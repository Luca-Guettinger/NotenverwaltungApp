package ch.lalumamesh.notenverwaltung.service;

import com.android.volley.VolleyError;

import java.util.function.Consumer;

import ch.lalumamesh.notenverwaltung.model.Pruefung;
import ch.lalumamesh.notenverwaltung.repository.PruefungenRepository;

public class PruefungenService {
    PruefungenRepository pruefungenRepository;

    public PruefungenService(PruefungenRepository pruefungenRepository) {
        this.pruefungenRepository = pruefungenRepository;
    }

    public void savePruefung(Pruefung pruefung, Consumer<String> onResponse, Consumer<VolleyError> onError, Consumer<Exception> onBodyException) {
        pruefungenRepository.savePruefung(pruefung, onResponse, onError, onBodyException);
    }
}
