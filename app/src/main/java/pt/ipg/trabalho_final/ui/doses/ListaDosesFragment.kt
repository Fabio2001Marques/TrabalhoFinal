package pt.ipg.trabalho_final.ui.doses

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.*
import pt.ipg.trabalho_final.databinding.FragmentListaDosesBinding


class ListaDosesFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {


    private lateinit var dosesViewModel: DosesViewModel
    private var _binding: FragmentListaDosesBinding? = null

    private var adapterDoses : AdapterDose? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_dose
        dosesViewModel =
            ViewModelProvider(this).get(DosesViewModel::class.java)

        _binding = FragmentListaDosesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewDoses = view.findViewById<RecyclerView>(R.id.recyclerViewDoses)
        adapterDoses = AdapterDose(this)
        recyclerViewDoses.adapter = adapterDoses
        recyclerViewDoses.layoutManager = LinearLayoutManager(requireContext())

        val loaderManager = LoaderManager.getInstance(this)

        loaderManager.initLoader(ID_LOADER_MANAGER_DOSES, null, this)

    }

    fun navegaNovaDose() {
        findNavController().navigate(R.id.action_listaDosesFragment_to_novaDoseFragment)
    }

    fun navegaAlterarDose() {
        findNavController().navigate(R.id.action_listaDosesFragment_to_editaDoseFragment)
    }

    fun navegaEliminarDose() {
        findNavController().navigate(R.id.action_listaDosesFragment_to_eliminaDoseFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_nova_dose -> navegaNovaDose()
            R.id.action_alterar_dose -> navegaAlterarDose()
            R.id.action_eliminar_dose -> navegaEliminarDose()
            else -> return false
        }
        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderCovid.ENDERECO_DOSES,
            TabelaDose.TODOS_CAMPOS,
            null, null,
            TabelaDose.CAMPO_EXTERNO_NOME_PESSOA
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterDoses!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterDoses!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_DOSES = 0
    }

}