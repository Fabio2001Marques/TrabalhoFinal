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


        val pessoa = DadosApp.pessoaSelecionada!!
        pessoa.nome = nome
        pessoa.contacto = contacto
        pessoa.morada = morada
        pessoa.campo_cc = campo_cc
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
