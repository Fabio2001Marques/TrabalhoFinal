package pt.ipg.trabalho_final

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterEnfermeiros (var cursor : Cursor? = null) : RecyclerView.Adapter<AdapterEnfermeiros.ViewHolderEnfermeiro>() {
    class ViewHolderEnfermeiro(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEnfermeiro {
        TODO("Not yet implemented")
    }


    override fun onBindViewHolder(holder: ViewHolderEnfermeiro, position: Int) {
        TODO("Not yet implemented")
    }


    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}