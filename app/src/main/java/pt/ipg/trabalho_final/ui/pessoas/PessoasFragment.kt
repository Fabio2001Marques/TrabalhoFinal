package pt.ipg.trabalho_final.ui.pessoas

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
import pt.ipg.trabalho_final.*
import pt.ipg.trabalho_final.databinding.FragmentPessoasBinding
import pt.ipg.trabalho_final.ui.enfermeiros.EnfermeirosFragment

class PessoasFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>{

    private lateinit var pessoasViewModel: PessoasViewModel
    private var _binding: FragmentPessoasBinding? = null

    private var adapterPessoas : AdapterPessoas? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pessoasViewModel =
            ViewModelProvider(this).get(PessoasViewModel::class.java)

        _binding = FragmentPessoasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewPessoas = view.findViewById<RecyclerView>(R.id.recyclerViewPessoas)
        adapterPessoas = AdapterPessoas()
        recyclerViewPessoas.adapter = adapterPessoas
        recyclerViewPessoas.layoutManager = LinearLayoutManager(requireContext())

        val loaderManager = LoaderManager.getInstance(this)

        loaderManager.initLoader(PessoasFragment.ID_LOADER_MANAGER_PESSOAS, null, this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        adapterPessoas!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterPessoas!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_PESSOAS = 0
    }
}