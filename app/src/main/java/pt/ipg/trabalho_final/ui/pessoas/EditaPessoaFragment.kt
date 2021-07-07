package pt.ipg.trabalho_final.ui.pessoas

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalho_final.ContentProviderCovid
import pt.ipg.trabalho_final.DadosApp
import pt.ipg.trabalho_final.MainActivity
import pt.ipg.trabalho_final.R

class EditaPessoaFragment : Fragment() {

    private lateinit var editTextNome: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var editTextCampoCC: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextMorada: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_pessoa
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_pessoa
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_pessoa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextPessoasEditaNome)
        editTextDataNascimento = view.findViewById(R.id.editTextPessoasEditaDataNascimento)
        editTextContacto = view.findViewById(R.id.editTextPessoasEditaContacto)
        editTextCampoCC = view.findViewById(R.id.editTextPessoasEditaCampoCC)
        editTextMorada = view.findViewById(R.id.editTextPessoasEditaMorada)

        editTextNome.setText(DadosApp.pessoaSelecionada!!.nome)
        editTextContacto.setText(DadosApp.pessoaSelecionada!!.contacto)
        editTextMorada.setText(DadosApp.pessoaSelecionada!!.morada)
        editTextDataNascimento.setText(DadosApp.pessoaSelecionada!!.data_nascimento.toString())
        editTextCampoCC.setText(DadosApp.pessoaSelecionada!!.campo_cc)

    }

    fun navegaListaPessoas() {
        findNavController().navigate(R.id.action_editaPessoaFragment_to_ListaPessoasFragment)
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
        val campocc = editTextCampoCC.text.toString()
        if (campocc.isEmpty()) {
            editTextCampoCC.setError(getString(R.string.preencha_CampoCC))
            editTextCampoCC.requestFocus()
            return
        }

        val dataNascimento = editTextDataNascimento.text.toString()
        if (dataNascimento.isEmpty()) {
            editTextDataNascimento.setError(getString(R.string.preencha_DataNascimento))
            editTextDataNascimento.requestFocus()
            return
        }


        val pessoa = DadosApp.pessoaSelecionada!!
        pessoa.nome = nome
        pessoa.contacto = contacto
        pessoa.morada = morada
        pessoa.campo_cc = campocc
        pessoa.data_nascimento = dataNascimento.toInt()

        val uriPessoa = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_PESSOAS,
            pessoa.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriPessoa,
            pessoa.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_pessoa,
                Toast.LENGTH_LONG
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
            R.id.action_guardar_edita_pessoa -> guardar()
            R.id.action_cancelar_edita_pessoa -> navegaListaPessoas()
            else -> return false
        }

        return true
    }

}
