package pt.ipg.trabalho_final

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.ui.pessoas.PessoasFragment

class AdapterPessoas (val fragment: PessoasFragment,var cursor : Cursor? = null) : RecyclerView.Adapter<AdapterPessoas.ViewHolderPessoa>() {
    class ViewHolderPessoa(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPessoa {
        val itemPessoa = fragment.layoutInflater.inflate(R.layout.item_pessoas, parent, false)

        return ViewHolderPessoa(itemPessoa)
    }


    override fun onBindViewHolder(holder: ViewHolderPessoa, position: Int) {
        TODO("Not yet implemented")
    }


    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}