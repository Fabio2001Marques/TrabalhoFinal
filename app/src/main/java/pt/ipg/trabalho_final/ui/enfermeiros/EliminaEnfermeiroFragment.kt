package pt.ipg.trabalho_final.ui.enfermeiros

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalho_final.ContentProviderCovid
import pt.ipg.trabalho_final.DadosApp
import pt.ipg.trabalho_final.MainActivity
import pt.ipg.trabalho_final.R

class EliminaEnfermeiroFragment : Fragment() {
    private lateinit var textViewNome: TextView
    private lateinit var textViewContacto: TextView
    private lateinit var textViewMorada: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_enfermeiro

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_enfermeiro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNome = view.findViewById(R.id.textView_enfermeiro_elimina_nome)
        textViewContacto = view.findViewById(R.id.textView_enfermeiro_elimina_contacto)
        textViewMorada= view.findViewById(R.id.textView_enfermeiro_elimina_morada)

        val enfermeiro = DadosApp.enfermeiroSelecionado!!
        textViewNome.setText(enfermeiro.nome)
        textViewContacto.setText(enfermeiro.contacto)
        textViewMorada.setText(enfermeiro.morada)
    }

    fun navegaListaEnfermeiros() {
        findNavController().navigate(R.id.action_eliminaEnfermeiroFragment_to_ListaEnfermeirosFragment)
    }

    fun elimina() {
        val uriLivro = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_ENFERMEIROS,
            DadosApp.enfermeiroSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriLivro,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_enfermeiro,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.enfermeiro_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaEnfermeiros()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_enfermeiro -> elimina()
            R.id.action_cancelar_eliminar_enfermeiro -> navegaListaEnfermeiros()
            else -> return false
        }

        return true
    }
}

