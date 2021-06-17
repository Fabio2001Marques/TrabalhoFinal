package pt.ipg.trabalho_final.ui

import pt.ipg.trabalho_final.Enfermeiro
import pt.ipg.trabalho_final.MainActivity
import pt.ipg.trabalho_final.Pessoa

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        var enfermeiroSelecionado : Enfermeiro? = null
        var pessoaSelecionada : Pessoa? = null
    }
}