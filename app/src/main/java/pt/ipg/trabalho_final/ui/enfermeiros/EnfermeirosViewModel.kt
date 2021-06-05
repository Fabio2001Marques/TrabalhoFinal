package pt.ipg.trabalho_final.ui.enfermeiros

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnfermeirosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Enfermeiros Fragment"
    }
    val text: LiveData<String> = _text
}