package pt.ipg.trabalho_final

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.ui.enfermeiros.EnfermeirosFragment

class AdapterEnfermeiros (val fragment: EnfermeirosFragment, var cursor : Cursor? = null) : RecyclerView.Adapter<AdapterEnfermeiros.ViewHolderEnfermeiro>() {
    class ViewHolderEnfermeiro(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewEnfermeirosNome)
        private val textViewContato = itemView.findViewById<TextView>(R.id.textViewEnfermeirosContato)
        private val textViewMorada = itemView.findViewById<TextView>(R.id.textViewEnfermeirosMorada)

        fun atualizaEnfermeiro(enfermeiro: Enfermeiro) {
            textViewNome.text = enfermeiro.nome
            textViewContato.text = enfermeiro.contacto
            textViewMorada.text = enfermeiro.morada
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEnfermeiro {
        val itemEnfermeiro = fragment.layoutInflater.inflate(R.layout.item_enfermeiros, parent, false)

        return ViewHolderEnfermeiro(itemEnfermeiro)
    }


    override fun onBindViewHolder(holder: ViewHolderEnfermeiro, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaEnfermeiro(Enfermeiro.fromCursor(cursor!!))
    }


    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}