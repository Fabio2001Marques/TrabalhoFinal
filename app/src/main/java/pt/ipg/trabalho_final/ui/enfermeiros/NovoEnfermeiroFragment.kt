package pt.ipg.trabalho_final.ui.enfermeiros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabalho_final.*
import pt.ipg.trabalho_final.databinding.FragmentNovoEnfermeiroBinding

class NovoEnfermeiroFragment : Fragment(){
    private var _binding: FragmentNovoEnfermeiroBinding? = null

    private lateinit var editTextNome: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextMorada: EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_enfermeiro

        _binding = FragmentNovoEnfermeiroBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextPessoasNome)
        editTextContacto = view.findViewById(R.id.editTextPessoasDataNascimento)
        editTextMorada = view.findViewById(R.id.editTextPessoasMorada)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaEnfermeiros() {
        findNavController().navigate(R.id.action_NovoEnfermeiroFragment_to_ListaEnfermeirosFragment)

    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        var pattern = Regex("[0-9]")
        if (nome.trim().isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_Nome))
            editTextNome.requestFocus()
            return
        }else if(pattern.containsMatchIn(nome)){
            editTextNome.setError(getString(R.string.nome_semLetras))
            editTextNome.requestFocus()
            return
        }

        val contacto = editTextContacto.text.toString()
        if (contacto.trim().isEmpty()) {
            editTextContacto.setError(getString(R.string.preencha_Contacto))
            editTextContacto.requestFocus()
            return
        }else if (contacto.length != 9){
            editTextContacto.setError(getString(R.string.Contacto_NoveDigitos))
            editTextContacto.requestFocus()
            return
        }

        val morada = editTextMorada.text.toString()
        if (morada.trim().isEmpty()) {
            editTextMorada.setError(getString(R.string.preencha_Morada))
            editTextMorada.requestFocus()
            return
        }


        val enfermeiro = Enfermeiro(nome = nome, contacto = contacto, morada = morada)

        val uri = activity?.contentResolver?.insert(
            ContentProviderCovid.ENDERECO_ENFERMEIROS,
            enfermeiro.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.erro_inserir_enfermeiro),
                Snackbar.LENGTH_LONG
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
            R.id.action_guardar_novo_enfermeiro -> guardar()
            R.id.action_cancelar_novo_enfermeiro -> navegaListaEnfermeiros()
            else -> return false
        }

        return true
    }
}