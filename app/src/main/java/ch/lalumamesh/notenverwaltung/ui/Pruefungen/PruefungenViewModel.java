package ch.lalumamesh.notenverwaltung.ui.Pruefungen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PruefungenViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PruefungenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Prüfungen fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}