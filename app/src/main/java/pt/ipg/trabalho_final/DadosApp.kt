package pt.ipg.trabalho_final

import pt.ipg.trabalho_final.ui.enfermeiros.ListaEnfermeirosFragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        var listaEnfermeirosFragment: ListaEnfermeirosFragment? = null
        var enfermeiroSelecionado : Enfermeiro? = null
        var pessoaSelecionada : Pessoa? = null
    }
}