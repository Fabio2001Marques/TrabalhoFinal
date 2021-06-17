package pt.ipg.trabalho_final

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.ui.DadosApp
import pt.ipg.trabalho_final.ui.enfermeiros.EnfermeirosFragment

class AdapterEnfermeiros (val fragment: EnfermeirosFragment) : RecyclerView.Adapter<AdapterEnfermeiros.ViewHolderEnfermeiro>() {

     var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderEnfermeiro(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewEnfermeirosNome)
        private val textViewContato = itemView.findViewById<TextView>(R.id.textViewEnfermeirosContato)
        private val textViewMorada = itemView.findViewById<TextView>(R.id.textViewEnfermeirosMorada)

        private lateinit var enfermeiro: Enfermeiro

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaEnfermeiro(enfermeiro: Enfermeiro) {
            this.enfermeiro =enfermeiro

            textViewNome.text = enfermeiro.nome
            textViewContato.text = enfermeiro.contacto
            textViewMorada.text = enfermeiro.morada
        }

        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.enfermeiroSelecionado = enfermeiro
            DadosApp.activity.atualizaMenuListaEnfermeiros(true)
        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderEnfermeiro? = null
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