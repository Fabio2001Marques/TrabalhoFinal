package pt.ipg.trabalho_final.ui.enfermeiros

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabalho_final.*


class EditaEnfermeiroFragment : Fragment(){
    private lateinit var editTextNome: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextMorada: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_enfermeiro
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_enfermeiro, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextEnfermeirosNome)
        editTextContacto = view.findViewById(R.id.editTextEnfermeirosContacto)
        editTextMorada = view.findViewById(R.id.editTextEnfermeirosMorada)

        editTextNome.setText(DadosApp.enfermeiroSelecionado!!.nome)
        editTextContacto.setText(DadosApp.enfermeiroSelecionado!!.contacto)
        editTextMorada.setText(DadosApp.enfermeiroSelecionado!!.morada)
    }

    fun navegaListaEnfermeiros() {
        findNavController().navigate(R.id.action_editaEnfermeiroFragment_to_ListaEnfermeirosFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_Nome))
            editTextNome.requestFocus()
            return
        }

        val contacto = editTextContacto.text.toString()
        if (contacto.isEmpty()) {
            editTextContacto.setError(getString(R.string.preencha_Contacto))
            editTextContacto.requestFocus()
            return
        }

        val morada = editTextMorada.text.toString()
        if (morada.isEmpty()) {
            editTextMorada.setError(getString(R.string.preencha_Morada))
            editTextMorada.requestFocus()
            return
        }

        val enfermeiro = DadosApp.enfermeiroSelecionado!!
        enfermeiro.nome = nome
        enfermeiro.contacto = contacto
        enfermeiro.morada = morada

        val uriEnfermeiro = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_ENFERMEIROS,
            enfermeiro.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriEnfermeiro,
            enfermeiro.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_enfermeiro,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.enfermeiro_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaEnfermeiros()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_enfermeiro -> guardar()
            R.id.action_cancelar_edita_enfermeiro -> navegaListaEnfermeiros()
            else -> return false
        }

        return true
    }

}
