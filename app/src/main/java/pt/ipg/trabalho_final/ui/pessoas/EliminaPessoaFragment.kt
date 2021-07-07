package pt.ipg.trabalho_final.ui.pessoas

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalho_final.ContentProviderCovid
import pt.ipg.trabalho_final.DadosApp
import pt.ipg.trabalho_final.MainActivity
import pt.ipg.trabalho_final.R


class EliminaPessoaFragment: Fragment() {

    private lateinit var editTextNome: TextView
    private lateinit var editTextDataNascimento: TextView
    private lateinit var editTextCampoCC: TextView
    private lateinit var editTextContacto: TextView
    private lateinit var editTextMorada: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_pessoa

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_pessoa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.textView_pessoa_elimina_nome)
        editTextDataNascimento = view.findViewById(R.id.textView_pessoa_elimina_dataNascimento)
        editTextContacto = view.findViewById(R.id.textView_pessoa_elimina_campocc)
        editTextCampoCC = view.findViewById(R.id.textView_pessoa_elimina_campocc)
        editTextMorada = view.findViewById(R.id.textView_pessoa_elimina_morada)

        val pessoa = DadosApp.pessoaSelecionada!!
        editTextNome.setText(pessoa.nome)
        editTextDataNascimento.setText(pessoa.data_nascimento.toString())
        editTextContacto.setText(pessoa.contacto)
        editTextCampoCC.setText(pessoa.campo_cc)
        editTextMorada.setText(pessoa.morada)
    }

    fun navegaListaPessoas() {
        findNavController().navigate(R.id.action_eliminaPessoaFragment_to_ListaPessoasFragment)
    }

    fun elimina() {
        val uriPessoa = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_PESSOAS,
            DadosApp.pessoaSelecionada!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriPessoa,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_pessoa,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.pessoa_eliminada_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaPessoas()

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_pessoa -> elimina()
            R.id.action_cancelar_eliminar_pessoa -> navegaListaPessoas()
            else -> return false
        }

        return true
    }
}