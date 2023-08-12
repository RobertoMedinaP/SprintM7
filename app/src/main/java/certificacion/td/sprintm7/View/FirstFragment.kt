package certificacion.td.sprintm7.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import certificacion.td.sprintm7.R
import certificacion.td.sprintm7.ViewModel.PlantViewModel
import certificacion.td.sprintm7.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PlantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = PlantAdapter()
        binding.rv1.adapter = adapter
        binding.rv1.layoutManager = GridLayoutManager(context, 2)

        viewModel.getPlantList().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateData(it)
            }
        })

        adapter.elementoSeleccionado().observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("******ELEGIR ID******", it.id.toString())
            }

            val bundle = Bundle().apply {
                putString("plantid", it.id.toString())
            }
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}