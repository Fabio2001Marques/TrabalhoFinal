package pt.ipg.trabalho_final.ui.vacinas

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.*
import pt.ipg.trabalho_final.databinding.FragmentListaVacinasBinding
import pt.ipg.trabalho_final.ui.home.VacinasViewModel

class ListaVacinasFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>{

    private lateinit var vacinasViewModel: VacinasViewModel
    private var _binding: FragmentListaVacinasBinding? = null

    private var adapterVacinas : AdapterVacinas? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_vacinas
        vacinasViewModel =
            ViewModelProvider(this).get(VacinasViewModel::class.java)

        _binding = FragmentListaVacinasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewVacinas = view.findViewById<RecyclerView>(R.id.recyclerViewVacinas)
        adapterVacinas = AdapterVacinas(this)
        recyclerViewVacinas.adapter = adapterVacinas
        recyclerViewVacinas.layoutManager = LinearLayoutManager(requireContext())

        val loaderManager = LoaderManager.getInstance(this)

        loaderManager.initLoader(ID_LOADER_MANAGER_VACINAS, null, this)

    }

    fun navegaNovaVacina() {
        findNavController().navigate(R.id.action_ListaVacinasFragment_to_novaVacinaFragment)
    }

    fun navegaAlterarVacina() {
        findNavController().navigate(R.id.action_ListaVacinasFragment_to_editaVacinaFragment)
    }

    fun navegaEliminarVacina() {
        //findNavController().navigate(R.id.action_ListaPessoasFragment_to_eliminaPessoaFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_nova_vacina -> navegaNovaVacina()
            R.id.action_alterar_vacinas -> navegaAlterarVacina()
            //R.id.action_eliminar_pessoas -> navegaEliminarVacina()
            else -> return false
        }
        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderCovid.ENDERECO_VACINAS,
            TabelaVacinas.TODOS_CAMPOS,
            null, null,
            TabelaVacinas.CAMPO_NOME
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterVacinas!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterVacinas!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_VACINAS = 0
    }
}