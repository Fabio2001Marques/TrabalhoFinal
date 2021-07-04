package pt.ipg.trabalho_final

import androidx.fragment.app.Fragment
import pt.ipg.trabalho_final.ui.enfermeiros.ListaEnfermeirosFragment
import pt.ipg.trabalho_final.ui.enfermeiros.NovoEnfermeiroFragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var enfermeiroSelecionado : Enfermeiro? = null
        var pessoaSelecionada : Pessoa? = null
    }
}