package pt.ipg.trabalho_final

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.ui.doses.ListaDosesFragment

class AdapterDose (val fragment: ListaDosesFragment) : RecyclerView.Adapter<AdapterDose.ViewHolderDose>() {
    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderDose(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewNumDose = itemView.findViewById<TextView>(R.id.textViewDoseNumDose)
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewDoseNome)
        private val textViewData = itemView.findViewById<TextView>(R.id.textViewDoseData)
        private val textViewHora = itemView.findViewById<TextView>(R.id.textViewDoseHora)
        private val textViewEnfermeiro = itemView.findViewById<TextView>(R.id.textViewDoseEnfermeiro)
        private val textViewVacina = itemView.findViewById<TextView>(R.id.textViewDoseVacina)

        private lateinit var dose: Dose

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaDose(dose: Dose) {
            this.dose = dose

            textViewNumDose.text = dose.num_dose.toString()
            textViewNome.text = dose.nomePessoa
            textViewData.text = dose.data.toString()
            textViewHora.text = dose.hora.toString()
            textViewEnfermeiro.text = dose.nomeEnfermeiro
            textViewVacina.text = dose.nomeVacina
        }

        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.doseSelecionada = dose
            DadosApp.activity.atualizaMenuListaDoses(true)
        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderDose? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDose {
        val itemDose = fragment.layoutInflater.inflate(R.layout.item_dose, parent, false)

        return ViewHolderDose(itemDose)
    }


    override fun onBindViewHolder(holder: ViewHolderDose, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaDose(Dose.fromCursor(cursor!!))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}