package pt.ipg.trabalho_final

import pt.ipg.trabalho_final.ui.enfermeiros.ListaEnfermeirosFragment
import pt.ipg.trabalho_final.ui.enfermeiros.NovoEnfermeiroFragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        var listaEnfermeirosFragment: ListaEnfermeirosFragment? = null
        var novoEnfermeiroFragment: NovoEnfermeiroFragment? = null

        var enfermeiroSelecionado : Enfermeiro? = null
        var pessoaSelecionada : Pessoa? = null
    }
}