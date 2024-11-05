package com.example.mobil_prog_beadando.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobil_prog_beadando.databinding.FragmentTripBinding
import com.example.mobil_prog_beadando.ShoppingListApplication
import com.example.mobil_prog_beadando.adapters.TripAdapter
import com.example.mobil_prog_beadando.viewmodels.TripViewModel
import com.example.mobil_prog_beadando.viewmodels.TripViewModelFactory
import kotlinx.coroutines.launch

class TripFragment: Fragment() {
    private var _binding: FragmentTripBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: TripViewModel by activityViewModels{
        TripViewModelFactory(
            (activity?.application as ShoppingListApplication).database.TripDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.tripRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val tripAdapter = TripAdapter(viewModel)
        recyclerView.adapter = tripAdapter
        lifecycle.coroutineScope.launch{
            viewModel.getTrips().collect{
                tripAdapter.submitList(it)
            }

        }
        binding.createTripButton.setOnClickListener{
            val action = TripFragmentDirections.actionTripFragmentToSetTripBudgetFragment()
            view.findNavController().navigate(action)
        }

    }
}
