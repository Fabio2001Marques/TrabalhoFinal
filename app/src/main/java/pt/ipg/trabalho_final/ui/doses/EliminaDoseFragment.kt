package pt.ipg.trabalho_final.ui.doses

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

class EliminaDoseFragment : Fragment() {
    private lateinit var textViewPessoa: TextView
    private lateinit var textViewData: TextView
    private lateinit var textViewHora: TextView
    private lateinit var textViewEnfermeiro: TextView
    private lateinit var textViewVacina: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_dose

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_dose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewPessoa = view.findViewById(R.id.textViewEliminaDosePessoa)
        textViewData = view.findViewById(R.id.textViewEliminaDoseData)
        textViewHora = view.findViewById(R.id.textViewEliminaDoseHora)
        textViewEnfermeiro = view.findViewById(R.id.textViewEliminaDoseEnfermeiro)
        textViewVacina = view.findViewById(R.id.textViewEliminaDoseVacina)

        val dose = DadosApp.doseSelecionada!!
        textViewData.setText(dose.data.toString())
        textViewHora.setText(dose.hora.toString())
        textViewPessoa.setText(dose.nomePessoa)
        textViewEnfermeiro.setText(dose.nomeEnfermeiro)
        textViewVacina.setText(dose.nomeVacina)
    }

    fun navegaListaDoses() {
        findNavController().navigate(R.id.action_eliminaDoseFragment_to_listaDosesFragment)
    }

    fun elimina() {
        val uriDose = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_DOSES,
            DadosApp.doseSelecionada!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriDose,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_dose,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.dose_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaDoses()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_dose -> elimina()
            R.id.action_cancelar_eliminar_dose -> navegaListaDoses()
            else -> return false
        }

        return true
    }
}