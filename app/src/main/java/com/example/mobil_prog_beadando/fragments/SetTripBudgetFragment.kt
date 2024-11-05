package com.example.mobil_prog_beadando.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import com.example.mobil_prog_beadando.R
import com.example.mobil_prog_beadando.databinding.FragmentSetBudgetBinding
import com.example.mobil_prog_beadando.ShoppingListApplication
import com.example.mobil_prog_beadando.database.trip.Trip
import com.example.mobil_prog_beadando.viewmodels.TripViewModel
import com.example.mobil_prog_beadando.viewmodels.TripViewModelFactory
import kotlinx.coroutines.launch
import java.util.*

class SetTripBudgetFragment: Fragment(R.layout.fragment_set_budget) {
    private var _binding: FragmentSetBudgetBinding? = null

    private val binding get() = _binding!!

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
        _binding = FragmentSetBudgetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        binding.setTripBudgetButton.setOnClickListener {
            val name = binding.tripName.text.toString()
            val budget = binding.setTripBudgetInput.text.toString().toFloat()
            val item = Trip(id = UUID.randomUUID() ,name = name, goal = budget, cost = 1.0.toFloat(), difference = 1.00.toFloat())
            lifecycle.coroutineScope.launch{
                viewModel.upsert(item)
            }
            val action = SetTripBudgetFragmentDirections.actionSetTripBudgetFragmentToShoppingListFragment(item.id.toString(), item.goal)
            view.findNavController().navigate(action)
        }
    }
}
