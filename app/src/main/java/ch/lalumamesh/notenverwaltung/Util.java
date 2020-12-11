package ch.lalumamesh.notenverwaltung;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Util {
    public static void DisplaySnackbar(View view, String text, int durration) {
        Snackbar make = Snackbar.make(view, text, durration);
        make.show();
    }
}
