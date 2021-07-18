package pt.ipg.trabalho_final.ui.vacinas

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
import pt.ipg.trabalho_final.databinding.FragmentNovaVacinaBinding

class NovaVacinaFragment : Fragment() {
    private var _binding: FragmentNovaVacinaBinding? = null

    private lateinit var editTextNome: EditText
    private lateinit var editTextQuantidade: EditText
    private lateinit var editTextData: EditText


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_vacina


        _binding = FragmentNovaVacinaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextVacinaNome)
        editTextData= view.findViewById(R.id.editTextVacinaData)
        editTextQuantidade= view.findViewById(R.id.editTextVacinaQuantidade)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaVacinas() {
        findNavController().navigate(R.id.action_novaVacinaFragment_to_ListaVacinasFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_Nome))
            editTextNome.requestFocus()
            return
        }
        val data = editTextData.text.toString()
        if (data.isEmpty()) {
            editTextData.setError(getString(R.string.preencha_Data))
            editTextData.requestFocus()
            return
        }

        val quantidade = editTextQuantidade.text.toString()
        if (quantidade.isEmpty()) {
            editTextQuantidade.setError(getString(R.string.preencha_Quantidade))
            editTextQuantidade.requestFocus()
            return
        }

        val vacina = Vacina(nome = nome, quantidade = quantidade.toInt(), data = data.toInt())
        val uri = activity?.contentResolver?.insert(
            ContentProviderCovid.ENDERECO_VACINAS,
            vacina.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.erro_inserir_vacina),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            R.string.vacina_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaVacinas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_nova_vacina -> guardar()
            R.id.action_cancelar_nova_vacina -> navegaListaVacinas()
            else -> return false
        }
        return true
    }
}