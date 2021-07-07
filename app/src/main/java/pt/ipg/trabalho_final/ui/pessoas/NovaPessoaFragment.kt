package pt.ipg.trabalho_final.ui.pessoas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalho_final.DadosApp
import pt.ipg.trabalho_final.MainActivity
import pt.ipg.trabalho_final.R
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
        // todo: guardar enfermeiro
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