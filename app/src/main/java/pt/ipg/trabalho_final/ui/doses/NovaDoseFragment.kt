package pt.ipg.trabalho_final.ui.doses

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabalho_final.*
import pt.ipg.trabalho_final.databinding.FragmentNovaDoseBinding
import pt.ipg.trabalho_final.databinding.FragmentNovaVacinaBinding

class NovaDoseFragment  : Fragment(), LoaderManager.LoaderCallbacks<Cursor>  {
    private var _binding: FragmentNovaDoseBinding? = null

    private lateinit var spinnerPessoa: Spinner
    private lateinit var editTextData: EditText
    private lateinit var editTextHora: EditText
    private lateinit var spinnerEnfermeiro: Spinner
    private lateinit var spinnerVacina: Spinner

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_dose


        _binding = FragmentNovaDoseBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextData = view.findViewById(R.id.editTextData)
        editTextHora = view.findViewById(R.id.editTextHora)
        spinnerPessoa = view.findViewById(R.id.spinnerPessoas)
        spinnerEnfermeiro = view.findViewById(R.id.spinnerEnfermeiros)
        spinnerVacina = view.findViewById(R.id.spinnerVacinas)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_PESSOA, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_ENFERMEIRO, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_VACINA, null, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaDoses() {
        findNavController().navigate(R.id.action_novaDoseFragment_to_listaDosesFragment)
    }

    fun guardar() {
       /* val nome = editTextNome.text.toString()
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
        navegaListaVacinas()*/
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_nova_dose -> guardar()
            R.id.action_cancelar_nova_dose -> navegaListaDoses()
            else -> return false
        }
        return true
    }
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderCovid.ENDERECO_PESSOAS,
            TabelaPessoas.TODOS_CAMPOS,
            null, null,
            TabelaPessoas.CAMPO_NOME
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        atualizaSpinnerPessoas(data)
        atualizaSpinnerEnfermeiros(data)
        atualizaSpinnerVacinas(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerPessoas(null)
        atualizaSpinnerEnfermeiros(null)
        atualizaSpinnerVacinas(null)
    }

    private fun atualizaSpinnerPessoas(data: Cursor?) {
        spinnerPessoa.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaPessoas.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    private fun atualizaSpinnerEnfermeiros(data: Cursor?) {
        spinnerEnfermeiro.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaEnfermeiros.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    private fun atualizaSpinnerVacinas(data: Cursor?) {
        spinnerVacina.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaVacinas.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    companion object {
        const val ID_LOADER_MANAGER_PESSOA = 0
        const val ID_LOADER_MANAGER_ENFERMEIRO = 0
        const val ID_LOADER_MANAGER_VACINA = 0
    }


}