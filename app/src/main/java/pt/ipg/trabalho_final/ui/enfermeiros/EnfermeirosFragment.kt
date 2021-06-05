package pt.ipg.trabalho_final.ui.enfermeiros

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.AdapterEnfermeiros
import pt.ipg.trabalho_final.ContentProviderCovid
import pt.ipg.trabalho_final.R
import pt.ipg.trabalho_final.TabelaEnfermeiros
import pt.ipg.trabalho_final.databinding.FragmentEnfermeirosBinding

class EnfermeirosFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var enfermeirosViewModel: EnfermeirosViewModel
    private var _binding: FragmentEnfermeirosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        enfermeirosViewModel =
            ViewModelProvider(this).get(EnfermeirosViewModel::class.java)

        _binding = FragmentEnfermeirosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewLivros = view.findViewById<RecyclerView>(R.id.recyclerViewEnfermeiros)
        recyclerViewLivros.adapter = AdapterEnfermeiros()
        recyclerViewLivros.layoutManager = LinearLayoutManager(requireContext())

        val loaderManager = LoaderManager.getInstance(this)

        loaderManager.initLoader(ID_LOADER_MANAGER_ENFERMEIROS, null, this)

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
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

    companion object{
        const val ID_LOADER_MANAGER_ENFERMEIROS = 0
    }
}