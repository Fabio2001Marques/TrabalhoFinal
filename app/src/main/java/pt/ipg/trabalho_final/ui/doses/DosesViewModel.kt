package pt.ipg.trabalho_final.ui.doses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DosesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Doses Fragment"
    }
    val text: LiveData<String> = _text
}

