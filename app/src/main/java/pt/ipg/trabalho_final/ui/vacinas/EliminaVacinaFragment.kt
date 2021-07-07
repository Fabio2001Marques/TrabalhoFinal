package pt.ipg.trabalho_final.ui.vacinas

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalho_final.ContentProviderCovid
import pt.ipg.trabalho_final.DadosApp
import pt.ipg.trabalho_final.MainActivity
import pt.ipg.trabalho_final.R

class EliminaVacinaFragment: Fragment() {

    private lateinit var editTextNome: TextView
    private lateinit var editTextData: TextView
    private lateinit var editTextQuantidade: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_vacina

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_vacina, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.textView_vacina_elimina_nome)
        editTextData = view.findViewById(R.id.textView_vacina_elimina_data)
        editTextQuantidade = view.findViewById(R.id.textView_vacina_elimina_quantidade)


        val vacina = DadosApp.vacinaSelecionada!!
        editTextNome.setText(vacina.nome)
        editTextQuantidade.setText(vacina.quantidade.toString())
        editTextData.setText(vacina.data.toString())
    }

        fun navegaListaVacinas() {
            findNavController().navigate(R.id.action_eliminaVacinaFragment_to_ListaVacinasFragment)
        }

        fun elimina() {
            val uriVacina = Uri.withAppendedPath(
                ContentProviderCovid.ENDERECO_VACINAS,
                DadosApp.vacinaSelecionada!!.id.toString()
            )

            val registos = activity?.contentResolver?.delete(
                uriVacina,
                null,
                null
            )

            if (registos != 1) {
                Toast.makeText(
                    requireContext(),
                    R.string.erro_eliminar_vacina,
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            Toast.makeText(
                requireContext(),
                R.string.vacina_eliminada_sucesso,
                Toast.LENGTH_LONG
            ).show()
            navegaListaVacinas()

        }

        fun processaOpcaoMenu(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_confirma_eliminar_vacina -> elimina()
                R.id.action_cancelar_eliminar_vacina -> navegaListaVacinas()
                else -> return false
            }
            return true
        }
}