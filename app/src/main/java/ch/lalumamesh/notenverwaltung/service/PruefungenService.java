package ch.lalumamesh.notenverwaltung.service;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import ch.lalumamesh.notenverwaltung.Config;
import ch.lalumamesh.notenverwaltung.Util;
import ch.lalumamesh.notenverwaltung.model.Fach;
import ch.lalumamesh.notenverwaltung.model.Pruefung;
import ch.lalumamesh.notenverwaltung.model.Semester;
import ch.lalumamesh.notenverwaltung.repository.PruefungenRepository;

public class PruefungenService {
    PruefungenRepository pruefungenRepository;

    public PruefungenService(PruefungenRepository pruefungenRepository) {
        this.pruefungenRepository = pruefungenRepository;
    }

    public void savePruefung(Pruefung pruefung, Consumer<String> onResponse, Consumer<VolleyError> onError, Consumer<Exception> onBodyException) {
        pruefungenRepository.savePruefung(pruefung, onResponse, onError, onBodyException);
    }

    public void loadPruefungen(Consumer<Pruefung[]> doWithPruefungen, Consumer<VolleyError> onError) {
        pruefungenRepository.loadPruefungen(doWithPruefungen, onError);
    }

    public String isValid(String titel, String note, Semester semester, Fach fach) {
        if (titel.isEmpty()) {
            return "Titel darf nicht Leer sein. ";
        }
        if (note.isEmpty()) {
            return"Note darf nicht Leer sein. ";
        }

        if (titel.startsWith("http://")) { //dieser code ist notfall code falls beim testen die URL zum Backend Service angepasst werden muss.
            Config.url = titel;
            return "Request URL wurde zu '" + titel + "' angepasst.";
        }

        try {
            double noteDouble = Double.parseDouble(note);
            if (noteDouble < 1 || noteDouble > 6) {
                return "Note eine Zahl zwischen 1 und 6 sein.";
            }
        } catch (NumberFormatException ex) {
            return "Note muss eine Zahl sein.";
        }
        return "";
    }

    public List<String> convertToView(Pruefung[] pruefungen) {
        HashMap<Long, Semester> semesterHashMap = new HashMap<>();
        HashMap<Long, Fach> fachHashMap = new HashMap<>();
        HashMap<Long, HashMap<Long, List<Pruefung>>> semesterFachPruefungenMap = new HashMap<>();// semester -> fach -> prüfung
        for (Pruefung pruefung : pruefungen) {
            if (!semesterFachPruefungenMap.containsKey(pruefung.getSemester().getId())) {
                semesterHashMap.put(pruefung.getSemester().getId(), pruefung.getSemester());
                semesterFachPruefungenMap.put(pruefung.getSemester().getId(), new HashMap<>());
            }
            HashMap<Long, List<Pruefung>> map = semesterFachPruefungenMap.get(pruefung.getSemester().getId());
            if (!map.containsKey(pruefung.getFach().getId())) {
                fachHashMap.put(pruefung.getFach().getId(), pruefung.getFach());
                map.put(pruefung.getFach().getId(), new ArrayList<>());
            }
            List<Pruefung> plist = map.get(pruefung.getFach().getId());
            plist.add(pruefung);
        }
        List<String> result = new ArrayList<>();
        for (Long semesterId : semesterFachPruefungenMap.keySet()) {
            HashMap<Long, List<Pruefung>> hashMap = semesterFachPruefungenMap.get(semesterId);
            result.add(prettyPrint(
                    semesterHashMap.get(semesterId).getName(),
                    calculateAverageForSemester(hashMap))
            );
            for (Long fachId : semesterFachPruefungenMap.get(semesterId).keySet()) {
                List<Pruefung> pruefungList = hashMap.get(fachId);
                result.add(prettyPrint("=" + fachHashMap.get(fachId).getName(), calculateAverageForFach(pruefungList)));

                for (Pruefung pruefung : pruefungList) {
                    result.add("==" + prettyPrint(pruefung.getTitle(), pruefung.getNote()));
                }
            }
        }
        result.add("======");
        result.add(prettyPrint("Gesamter Schnitt: ", calculateAverageOverall(pruefungen)));

        return result;
    }

    public static double calculateAverageOverall(Pruefung[] pruefungArray) {
        double note = 0;
        double amount = 0;
        for (Pruefung pruefung : pruefungArray) {
            note += pruefung.getNote();
            amount++;
        }
        return note / amount;
    }

    public static double calculateAverageForFach(List<Pruefung> list) {
        double note = 0;
        double amount = 0;
        for (Pruefung pruefung : list) {
            note += pruefung.getNote();
            amount++;
        }
        return note / amount;
    }

    public static double calculateAverageForSemester(HashMap<Long, List<Pruefung>> hashMap) {
        double note = 0;
        double amount = 0;
        for (List<Pruefung> value : hashMap.values()) {
            for (Pruefung pruefung : value) {
                note += pruefung.getNote();
                amount++;
            }
        }
        return note / amount;
    }

    public static String prettyPrint(String name, double note) {
        return name + "      " + note + " -> " + roundToHalf(note);
    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }
}
