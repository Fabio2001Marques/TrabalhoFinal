package pt.ipg.trabalho_final.ui.doses

import android.database.Cursor
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
import pt.ipg.trabalho_final.*

class EditaDoseFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>  {

    private lateinit var spinnerPessoa: Spinner
    private lateinit var editTextData: EditText
    private lateinit var editTextHora: EditText
    private lateinit var spinnerEnfermeiro: Spinner
    private lateinit var spinnerVacina: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_dose
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_dose
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_dose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextData = view.findViewById(R.id.editTextEditaDoseData)
        editTextHora = view.findViewById(R.id.editTextEditaDoseHora)
        spinnerPessoa = view.findViewById(R.id.spinnerEditaDosePessoas)
        spinnerEnfermeiro = view.findViewById(R.id.spinnerEditaDoseEnfermeiros)
        spinnerVacina = view.findViewById(R.id.spinnerEditaDoseVacinas)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_PESSOA, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_ENFERMEIRO, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_VACINA, null, this)

        editTextData.setText(DadosApp.doseSelecionada!!.data.toString())
        editTextHora.setText(DadosApp.doseSelecionada!!.hora.toString())

    }

    fun navegaListaDoses() {
        findNavController().navigate(R.id.action_editaDoseFragment_to_listaDosesFragment)
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
        val idEnfermeiro = spinnerEnfermeiro.selectedItemId
        val idVacina = spinnerVacina.selectedItemId

        val dose = DadosApp.doseSelecionada!!
        dose.data = data.toInt()
        dose.hora = hora.toInt()
        dose.id_pessoas = idPessoa
        dose.id_enfermeiros = idEnfermeiro
        dose.id_vacinas = idVacina

        val uriDose = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_DOSES,
            dose.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriDose,
            dose.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_dose,
                Toast.LENGTH_LONG
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
            R.id.action_guardar_edita_dose -> guardar()
            R.id.action_cancelar_edita_dose -> navegaListaDoses()
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