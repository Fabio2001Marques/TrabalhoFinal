package pt.ipg.trabalho_final

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.ui.enfermeiros.EnfermeirosFragment

class AdapterEnfermeiros (val fragment: EnfermeirosFragment, var cursor : Cursor? = null) : RecyclerView.Adapter<AdapterEnfermeiros.ViewHolderEnfermeiro>() {
    class ViewHolderEnfermeiro(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEnfermeiro {
        val itemEnfermeiro = fragment.layoutInflater.inflate(R.layout.item_enfermeiros, parent, false)

        return ViewHolderEnfermeiro(itemEnfermeiro)
    }


    override fun onBindViewHolder(holder: ViewHolderEnfermeiro, position: Int) {
        TODO("Not yet implemented")
    }


    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}