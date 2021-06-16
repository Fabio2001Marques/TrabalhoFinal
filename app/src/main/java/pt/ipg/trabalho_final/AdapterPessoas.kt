package pt.ipg.trabalho_final

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterPessoas (var cursor : Cursor? = null) : RecyclerView.Adapter<AdapterPessoas.ViewHolderPessoa>() {
    class ViewHolderPessoa(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPessoa {
        TODO("Not yet implemented")
    }


    override fun onBindViewHolder(holder: ViewHolderPessoa, position: Int) {
        TODO("Not yet implemented")
    }


    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}