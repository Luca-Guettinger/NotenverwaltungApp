package ch.lalumamesh.notenverwaltung.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ch.lalumamesh.notenverwaltung.Config;
import ch.lalumamesh.notenverwaltung.Util;
import ch.lalumamesh.notenverwaltung.MyApplication;
import ch.lalumamesh.notenverwaltung.R;
import ch.lalumamesh.notenverwaltung.model.Fach;
import ch.lalumamesh.notenverwaltung.model.Pruefung;
import ch.lalumamesh.notenverwaltung.model.Semester;
import ch.lalumamesh.notenverwaltung.repository.PruefungenRepository;
import ch.lalumamesh.notenverwaltung.repository.StammdatenRepository;

public class HomeFragment extends Fragment {
    private final StammdatenRepository stammdatenRepository;
    private final PruefungenRepository pruefungenRepository;
    private Semester[] semester;
    private Fach[] faecher;

    public HomeFragment() {
        stammdatenRepository = new StammdatenRepository(MyApplication.getAppContext());
        pruefungenRepository = new PruefungenRepository(MyApplication.getAppContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Spinner semester = root.findViewById(R.id.spinner_semester);
        Spinner fach = root.findViewById(R.id.spinner_fach);
        EditText titel = root.findViewById(R.id.text_title);
        EditText note = root.findViewById(R.id.number_note);

        Button button = root.findViewById(R.id.button_save);

        setupButton(root, semester, fach, titel, note, button);

        setupSemester(root, semester);
        setupFaecher(root, fach);
        return root;
    }

    private void setupButton(View root, Spinner semester, Spinner fach, EditText titel, EditText note, Button button) {
        button.setOnClickListener(v -> {
            double noteDouble;

            if (checkEmpty(root, titel, note)) return;

            if (titel.getText().toString().startsWith("http://")) {
                Config.url = titel.getText().toString();
                Util.DisplaySnackbar(root, "Request URL wurde zu '" + titel.getText().toString() + "' angepasst.", 2000);
                return;
            }

            try {
                noteDouble = Double.parseDouble(note.getText().toString());
                if (noteDouble < 1 || noteDouble > 6) {
                    Util.DisplaySnackbar(root, "Note eine Zahl zwischen 1 und 6 sein.", 2000);
                    return;
                }
            } catch (NumberFormatException ex) {
                Util.DisplaySnackbar(root, "Note muss eine Zahl sein. ", 2000);
                return;
            }
            Pruefung pruefung = new Pruefung(null, titel.getText().toString(), noteDouble, ((Semester) semester.getSelectedItem()), ((Fach) fach.getSelectedItem()));
            System.out.println(pruefung);
            pruefungenRepository.savePruefung(pruefung,
                    s -> {
                        titel.getText().clear();
                        note.getText().clear();
                        Util.DisplaySnackbar(root, "Erfoglreich gespeichert", 2000);
                    }, volleyError -> {
                        Util.DisplaySnackbar(root, "Es ist ein Fehler aufgetreten: " + volleyError.getMessage(), 2000);
                    }, e -> {
                        Util.DisplaySnackbar(root, "Es ist ein Fehler aufgetreten: " + e.getMessage(), 2000);
                        e.printStackTrace();
                    });
        });
    }

    private boolean checkEmpty(View root, EditText titel, EditText note) {
        if (titel.getText().toString().isEmpty()) {
            Util.DisplaySnackbar(root, "Titel darf nicht Leer sein. ", 2000);
            return true;
        }
        if (note.getText().toString().isEmpty()) {
            Config.url = titel.getText().toString();
            Util.DisplaySnackbar(root, "Note darf nicht Leer sein. ", 2000);
            return true;
        }
        return false;
    }

    private void setupFaecher(View root, Spinner fach) {
        stammdatenRepository.loadFach(faecher -> {
            this.faecher = faecher;
            ArrayAdapter<Fach> fachArrayAdapter = new ArrayAdapter<>(MyApplication.getAppContext(),
                    android.R.layout.simple_spinner_dropdown_item, faecher);
            fachArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fach.setAdapter(fachArrayAdapter);
        }, volleyError -> {
            Util.DisplaySnackbar(root, "Fehler beim laden der Faecher, ist das Backend verfügbar? ", 2000);
        });
    }

    private void setupSemester(View root, Spinner semester) {
        stammdatenRepository.loadSemester(semesters -> {
            this.semester = semesters;
            ArrayAdapter<Semester> semesterArrayAdapter = new ArrayAdapter<>(MyApplication.getAppContext(),
                    android.R.layout.simple_spinner_dropdown_item, semesters);
            semesterArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            semester.setAdapter(semesterArrayAdapter);
        }, volleyError -> {
            Util.DisplaySnackbar(root, "Fehler beim laden der Semester, ist das Backend verfügbar? ", 2000);
        });
    }
}