package pt.ipg.trabalho_final

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.trabalho_final.ui.pessoas.PessoasFragment

class AdapterPessoas (val fragment: PessoasFragment) : RecyclerView.Adapter<AdapterPessoas.ViewHolderPessoa>() {

     var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderPessoa(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener  {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewPessoaNome)
        private val textViewNascimento = itemView.findViewById<TextView>(R.id.textViewPessoaNascimento)
        private val textViewContato = itemView.findViewById<TextView>(R.id.textViewPessoaContacto)
        private val textViewMorada = itemView.findViewById<TextView>(R.id.textViewPessoaMorada)

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaPessoa(pessoa: Pessoa) {
            textViewNome.text = pessoa.nome
            textViewNascimento.text = pessoa.data_nascimento.toString()
            textViewContato.text = pessoa.contacto
            textViewMorada.text = pessoa.morada
        }

        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }
        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderPessoa? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPessoa {
        val itemPessoa = fragment.layoutInflater.inflate(R.layout.item_pessoas, parent, false)

        return ViewHolderPessoa(itemPessoa)
    }


    override fun onBindViewHolder(holder: ViewHolderPessoa, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaPessoa(Pessoa.fromCursor(cursor!!))
    }


    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}