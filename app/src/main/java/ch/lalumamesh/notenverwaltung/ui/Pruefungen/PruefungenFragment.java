package ch.lalumamesh.notenverwaltung.ui.Pruefungen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ch.lalumamesh.notenverwaltung.R;

public class PruefungenFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pruefungen, container, false);
        return root;
    }
}