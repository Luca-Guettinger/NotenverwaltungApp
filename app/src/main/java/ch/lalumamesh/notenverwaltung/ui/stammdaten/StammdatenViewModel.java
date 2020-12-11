package ch.lalumamesh.notenverwaltung.ui.stammdaten;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StammdatenViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StammdatenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is stammdaten fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}