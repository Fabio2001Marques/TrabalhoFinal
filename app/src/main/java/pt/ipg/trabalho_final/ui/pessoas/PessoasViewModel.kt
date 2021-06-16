package pt.ipg.trabalho_final.ui.pessoas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PessoasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pessoas Fragment"
    }
    val text: LiveData<String> = _text
}