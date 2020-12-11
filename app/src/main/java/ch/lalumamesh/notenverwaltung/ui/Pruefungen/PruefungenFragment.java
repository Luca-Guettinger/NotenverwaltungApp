package ch.lalumamesh.notenverwaltung.ui.Pruefungen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import ch.lalumamesh.notenverwaltung.MyApplication;
import ch.lalumamesh.notenverwaltung.R;
import ch.lalumamesh.notenverwaltung.Util;
import ch.lalumamesh.notenverwaltung.repository.PruefungenRepository;
import ch.lalumamesh.notenverwaltung.service.PruefungenService;

import static android.R.layout.simple_list_item_1;

public class PruefungenFragment extends Fragment {
    private final PruefungenService pruefungenService;

    public PruefungenFragment() {
        this.pruefungenService = new PruefungenService(new PruefungenRepository(MyApplication.getAppContext()));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pruefungen, container, false);
        pruefungenService.loadPruefungen(list -> {
            List<String> values = pruefungenService.convertToView(list);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MyApplication.getAppContext(), simple_list_item_1, values);
            ListView listView = root.findViewById(R.id.pruefungen_list);
            listView.setAdapter(adapter);
        }, volleyError -> {
            Util.DisplaySnackbar(root, "Es ist ein Fehler aufgeterten", 2000);
        });

        return root;
    }
}