package pt.ipg.trabalho_final.ui.doses

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
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
       val data = editTextData.text.toString()
        if (data.isEmpty()) {
            editTextData.setError(getString(R.string.preencha_Data))
            editTextData.requestFocus()
            return
        }
        val hora = editTextHora.text.toString()
        if (hora.isEmpty()) {
            editTextHora.setError(getString(R.string.preencha_Hora))
            editTextHora.requestFocus()
            return
        }
        val idPessoa = spinnerPessoa.selectedItemId

        val uridose = ContentProviderCovid.ENDERECO_DOSES_NUM_DOSES

        val registos = activity?.contentResolver?.query(uridose,null,null,null,null)
        var num_dose = 0
        registos!!.moveToFirst()
        while (registos.getLong(0) != null){
            num_dose++
            registos.moveToNext()
        }

        val idEnfermeiro = spinnerEnfermeiro.selectedItemId
        val idVacina = spinnerVacina.selectedItemId

        val dose = Dose(num_dose = num_dose ,data = data.toInt(), hora = hora.toInt(), id_pessoas = idPessoa, id_enfermeiros = idEnfermeiro, id_vacinas = idVacina)

        val uri = activity?.contentResolver?.insert(
            ContentProviderCovid.ENDERECO_DOSES,
            dose.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextData,
                getString(R.string.erro_inserir_dose),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            R.string.dose_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaDoses()
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
        when(id){
            0 -> return CursorLoader(
                requireContext(),
                ContentProviderCovid.ENDERECO_PESSOAS,
                TabelaPessoas.TODOS_CAMPOS,
                null, null,
                TabelaPessoas.CAMPO_NOME
            )

            1 -> return CursorLoader(
                requireContext(),
                ContentProviderCovid.ENDERECO_ENFERMEIROS,
                TabelaEnfermeiros.TODOS_CAMPOS,
                null, null,
                TabelaEnfermeiros.CAMPO_NOME
            )

            else -> return CursorLoader(
                requireContext(),
                ContentProviderCovid.ENDERECO_VACINAS,
                TabelaVacinas.TODOS_CAMPOS,
                null, null,
                TabelaVacinas.CAMPO_NOME
            )

        }

    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        when(loader.id){
            0 -> atualizaSpinnerPessoas(data)
            1 -> atualizaSpinnerEnfermeiros(data)
            2 -> atualizaSpinnerVacinas(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        when(loader.id){
            0 -> atualizaSpinnerPessoas(null)
            1 -> atualizaSpinnerEnfermeiros(null)
            2 -> atualizaSpinnerVacinas(null)
        }
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
        const val ID_LOADER_MANAGER_ENFERMEIRO = 1
        const val ID_LOADER_MANAGER_VACINA = 2
    }


}