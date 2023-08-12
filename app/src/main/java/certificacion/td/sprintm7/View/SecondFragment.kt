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
import certificacion.td.sprintm7.R
import certificacion.td.sprintm7.ViewModel.PlantViewModel
import certificacion.td.sprintm7.databinding.FragmentSecondBinding
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: PlantViewModel by activityViewModels()
    private var plantId: String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        // Recibiendo del primer fragmento
        arguments?.let { bundle ->
            plantId = bundle.getString("plantid")
            Log.d("***LO RECIBO??***", plantId.toString())
            //recibo bien :-)
        }

        plantId?.let { id->
            viewModel.getPlantByIdFromInternet(id)
        }

        viewModel.getPlantId().observe(viewLifecycleOwner, Observer {

            //Cargar los textos e imagenes
            Glide.with(binding.imageView).load(it.imagen).into(binding.imageView)
            binding.tv1.text=it.id.toString()
            binding.tv2.text=it.nombre
            binding.tv3.text=it.tipo
            binding.tv4.text=it.descripcion


        })



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}