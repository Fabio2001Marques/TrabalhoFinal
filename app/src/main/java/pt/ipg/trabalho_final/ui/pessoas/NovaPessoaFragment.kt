package pt.ipg.trabalho_final.ui.pessoas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabalho_final.*
import pt.ipg.trabalho_final.databinding.FragmentNovoPessoasBinding

class NovaPessoaFragment : Fragment() {
    private var _binding: FragmentNovoPessoasBinding? = null

    private lateinit var editTextNome: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var editTextMorada: EditText
    private lateinit var editTextCampoCC: EditText
    private lateinit var editTextContacto: EditText


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_pessoa


        _binding = FragmentNovoPessoasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextPessoasNome)
        editTextDataNascimento= view.findViewById(R.id.editTextPessoasDataNascimento)
        editTextContacto = view.findViewById(R.id.editTextPessoasContacto)
        editTextCampoCC= view.findViewById(R.id.editTextPessoasCampoCC)
        editTextMorada = view.findViewById(R.id.editTextPessoasMorada)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaPessoas() {
        findNavController().navigate(R.id.action_novaPessoaFragment_to_ListaPessoasFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_Nome))
            editTextNome.requestFocus()
            return
        }
        val dataNascimento = editTextDataNascimento.text.toString()
        if (dataNascimento.isEmpty()) {
            editTextDataNascimento.setError(getString(R.string.preencha_DataNascimento))
            editTextDataNascimento.requestFocus()
            return
        }

        val campo_cc = editTextCampoCC.text.toString()
        if (campo_cc.isEmpty()) {
            editTextCampoCC.setError(getString(R.string.preencha_CampoCC))
            editTextCampoCC.requestFocus()
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


        val pessoa = Pessoa(nome = nome, data_nascimento = 5032000,morada = "Rua Teste", campo_cc = "98745632",contacto = "963258741" )

        val uri = activity?.contentResolver?.insert(
            ContentProviderCovid.ENDERECO_PESSOAS,
            pessoa.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.erro_inserir_pessoa),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        navegaListaPessoas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_nova_pessoa -> guardar()
            R.id.action_cancelar_nova_pessoa -> navegaListaPessoas()
            else -> return false
        }
        return true
    }
}