package pt.ipg.trabalho_final.ui.vacinas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pt.ipg.trabalho_final.databinding.FragmentListaVacinasBinding
import pt.ipg.trabalho_final.ui.home.VacinasViewModel

class ListaVacinasFragment : Fragment() {

    private lateinit var vacinasViewModel: VacinasViewModel
    private var _binding: FragmentListaVacinasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vacinasViewModel =
            ViewModelProvider(this).get(VacinasViewModel::class.java)

        _binding = FragmentListaVacinasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}