package pt.ipg.trabalho_final

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.ui.vacinas.ListaVacinasFragment

class AdapterVacinas (val fragmentLista: ListaVacinasFragment) : RecyclerView.Adapter<AdapterVacinas.ViewHolderVacina>() {

    var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderVacina(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener  {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewVacinaNome)
        private val textViewQuantidade = itemView.findViewById<TextView>(R.id.textViewVacinaQuantidade)
        private val textViewData = itemView.findViewById<TextView>(R.id.textViewVacinaData)

        private lateinit var vacina: Vacina

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaVacina(vacina: Vacina) {
            this.vacina = vacina

            textViewNome.text = vacina.nome
            textViewQuantidade.text = vacina.quantidade.toString()
            textViewData.text = vacina.data.toString()
        }

        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }
        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.vacinaSelecionada = vacina
            //DadosApp.activity.atualizaMenuListaVacinas(true)
        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderVacina? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVacina {
        val itemVacina = fragmentLista.layoutInflater.inflate(R.layout.item_vacinas, parent, false)

        return ViewHolderVacina(itemVacina)
    }


    override fun onBindViewHolder(holder: ViewHolderVacina, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaVacina(Vacina.fromCursor(cursor!!))
    }


    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}