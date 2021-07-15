package pt.ipg.trabalho_final.ui.doses

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import pt.ipg.trabalho_final.AdapterVacinas
import pt.ipg.trabalho_final.databinding.FragmentListaVacinasBinding
import pt.ipg.trabalho_final.ui.home.VacinasViewModel

class ListaDosesFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

}