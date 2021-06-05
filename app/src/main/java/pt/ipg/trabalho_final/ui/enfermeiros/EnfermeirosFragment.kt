package pt.ipg.trabalho_final.ui.enfermeiros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pt.ipg.trabalho_final.databinding.FragmentEnfermeirosBinding

class EnfermeirosFragment : Fragment() {

    private lateinit var enfermeirosViewModel: EnfermeirosViewModel
    private var _binding: FragmentEnfermeirosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        enfermeirosViewModel =
            ViewModelProvider(this).get(EnfermeirosViewModel::class.java)

        _binding = FragmentEnfermeirosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}