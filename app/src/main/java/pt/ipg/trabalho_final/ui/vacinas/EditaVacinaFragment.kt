package pt.ipg.trabalho_final.ui.vacinas

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalho_final.ContentProviderCovid
import pt.ipg.trabalho_final.DadosApp
import pt.ipg.trabalho_final.MainActivity
import pt.ipg.trabalho_final.R

class EditaVacinaFragment: Fragment() {

    private lateinit var editTextNome: EditText
    private lateinit var editTextData: EditText
    private lateinit var editTextQuantidade: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_vacina
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_vacina
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_vacina, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextVacinaEditaNome)
        editTextQuantidade = view.findViewById(R.id.editTextVacinaEditaQuantidade)
        editTextData = view.findViewById(R.id.editTextVacinaEditaData)


        editTextNome.setText(DadosApp.vacinaSelecionada!!.nome)
        editTextQuantidade.setText(DadosApp.vacinaSelecionada!!.quantidade.toString())
        editTextData.setText(DadosApp.vacinaSelecionada!!.data.toString())


    }

    fun navegaListaVacina() {
        findNavController().navigate(R.id.action_editaVacinaFragment_to_ListaVacinasFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_Nome))
            editTextNome.requestFocus()
            return
        }

        val quantidade = editTextQuantidade.text.toString()
        if (quantidade.isEmpty()) {
            editTextQuantidade.setError(getString(R.string.preencha_Quantidade))
            editTextQuantidade.requestFocus()
            return
        }

        val data = editTextData.text.toString()
        if (data.isEmpty()) {
            editTextData.setError(getString(R.string.preencha_Data))
            editTextData.requestFocus()
            return
        }else if (data.length != 8 ){
            editTextData.setError(getString(R.string.data_erro_tamanho))
            editTextData.requestFocus()
            return
        }else if (((data.toInt() / 1000000)  > 31) || ((data.toInt() / 1000000)  < 1)){
            editTextData.setError(getString(R.string.erro_dia))
            editTextData.requestFocus()
            return
        }else if ((data.substring(2,4).toInt() > 12) || (data.substring(2,4).toInt() < 1)){
            editTextData.setError(getString(R.string.erro_mes))
            editTextData.requestFocus()
            return
        }else if ((data.toInt() % 10000 > 2021) || (data.toInt() % 10000 < 1900)){
            editTextData.setError(getString(R.string.erro_ano))
            editTextData.requestFocus()
            return
        }

        val vacina = DadosApp.vacinaSelecionada!!
        vacina.nome = nome
        vacina.quantidade = quantidade.toInt()
        vacina.data = data.toInt()


        val uriVacina = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_VACINAS,
            vacina.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriVacina,
            vacina.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_vacina,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.vacina_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaVacina()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_vacina -> guardar()
            R.id.action_cancelar_edita_vacina -> navegaListaVacina()
            else -> return false
        }

        return true
    }
}