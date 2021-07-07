package pt.ipg.trabalho_final.ui.enfermeiros

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
import pt.ipg.trabalho_final.databinding.FragmentEnfermeirosBinding

class ListaEnfermeirosFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var enfermeirosViewModel: EnfermeirosViewModel
    private var _binding: FragmentEnfermeirosBinding? = null

    private var adapterEnfermeiros : AdapterEnfermeiros? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_enfermeiros

        enfermeirosViewModel =
            ViewModelProvider(this).get(EnfermeirosViewModel::class.java)

        _binding = FragmentEnfermeirosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewEnfermeiros = view.findViewById<RecyclerView>(R.id.recyclerViewEnfermeiros)
        adapterEnfermeiros = AdapterEnfermeiros(this)
        recyclerViewEnfermeiros.adapter = adapterEnfermeiros
        recyclerViewEnfermeiros.layoutManager = LinearLayoutManager(requireContext())

        val loaderManager = LoaderManager.getInstance(this)

        loaderManager.initLoader(ID_LOADER_MANAGER_ENFERMEIROS, null, this)

    }

    fun navegaNovoEnfermeiro() {
        findNavController().navigate(R.id.action_ListaEnfermeirosFragment_to_NovoEnfermeiroFragment)
    }

    fun navegaAlterarEnfermeiro() {
        findNavController().navigate(R.id.action_ListaEnfermeirosFragment_to_editaEnfermeiroFragment)
    }

    fun navegaEliminarEnfermeiro() {
        findNavController().navigate(R.id.action_ListaEnfermeirosFragment_to_eliminaEnfermeiroFragment)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_novo_enfermeiros -> navegaNovoEnfermeiro()
            R.id.action_alterar_enfermeiros -> navegaAlterarEnfermeiro()
            R.id.action_eliminar_enfermeiro -> navegaEliminarEnfermeiro()
            else -> return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderCovid.ENDERECO_ENFERMEIROS,
            TabelaEnfermeiros.TODOS_CAMPOS,
            null, null,
            TabelaEnfermeiros.CAMPO_NOME
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterEnfermeiros!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterEnfermeiros!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_ENFERMEIROS = 0
    }
}