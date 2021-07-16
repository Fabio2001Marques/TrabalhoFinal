package pt.ipg.trabalho_final.ui.vacinas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VacinasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Vacinas Fragment"
    }
    val text: LiveData<String> = _text
}