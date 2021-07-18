package pt.ipg.trabalho_final.ui.pessoas

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
import pt.ipg.trabalho_final.databinding.FragmentNovoPessoasBinding
import java.util.*

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

        val dataNascimento = editTextDataNascimento.text.toString()
        if (dataNascimento.trim().isEmpty()) {
            editTextDataNascimento.setError(getString(R.string.preencha_DataNascimento))
            editTextDataNascimento.requestFocus()
            return
        }else if (dataNascimento.length != 8 ){
            editTextDataNascimento.setError(getString(R.string.data_erro_tamanho))
            editTextDataNascimento.requestFocus()
            return
        }else if (((dataNascimento.toInt() / 1000000)  > 31) || ((dataNascimento.toInt() / 1000000)  < 1)){
            editTextDataNascimento.setError(getString(R.string.erro_dia))
            editTextDataNascimento.requestFocus()
            return
        }else if ((dataNascimento.substring(2,4).toInt() > 12) || (dataNascimento.substring(2,4).toInt() < 1)){
            editTextDataNascimento.setError(getString(R.string.erro_mes))
            editTextDataNascimento.requestFocus()
            return
        }else if ((dataNascimento.toInt() % 10000 > 2021) || (dataNascimento.toInt() % 10000 < 1900)){
            editTextDataNascimento.setError(getString(R.string.erro_ano))
            editTextDataNascimento.requestFocus()
            return
        }

        val morada = editTextMorada.text.toString()
        if (morada.trim().isEmpty()) {
            editTextMorada.setError(getString(R.string.preencha_Morada))
            editTextMorada.requestFocus()
            return
        }

        val campo_cc = editTextCampoCC.text.toString()
        if (campo_cc.trim().isEmpty()) {
            editTextCampoCC.setError(getString(R.string.preencha_CampoCC))
            editTextCampoCC.requestFocus()
            return
        }else if (campo_cc.length != 8){
            editTextCampoCC.setError(getString(R.string.CampoCC_invalido))
            editTextCampoCC.requestFocus()
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


        val pessoa = Pessoa(nome = nome, data_nascimento = dataNascimento.toInt(), morada = morada, campo_cc = campo_cc,contacto = contacto )

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
        Toast.makeText(
            requireContext(),
            R.string.pessoa_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
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