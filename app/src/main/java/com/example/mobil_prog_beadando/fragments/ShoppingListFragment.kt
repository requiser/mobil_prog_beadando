package com.example.mobil_prog_beadando.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobil_prog_beadando.databinding.FragmentShoppingListBinding
import com.example.mobil_prog_beadando.ShoppingListApplication
import com.example.mobil_prog_beadando.adapters.ShoppingListAdapter
import com.example.mobil_prog_beadando.database.shoppinglist.ShoppingList
import com.example.mobil_prog_beadando.viewmodels.ShoppingListViewModel
import com.example.mobil_prog_beadando.viewmodels.ShoppingListViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ShoppingListFragment: Fragment() {

    companion object {
        var TRIP_ID  = "tripId"
        var TRIP_AMOUNT = "tripAmount"
    }
    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var tripId: UUID

    private var tripAmount: Float = 0.00.toFloat()

    private val viewModel: ShoppingListViewModel by activityViewModels {
        ShoppingListViewModelFactory(
            (activity?.application as ShoppingListApplication).database.ShoppingDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        arguments?.let{
            tripId = UUID.fromString(it.getString(TRIP_ID))
            tripAmount = it.getFloat(TRIP_AMOUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){ super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val shoppingListAdapter = ShoppingListAdapter(viewModel)
        recyclerView.adapter = shoppingListAdapter
        lifecycle.coroutineScope.launch{
            viewModel.getShoppingListByTrip(tripId).collect {
                var sum: Float = 0.00.toFloat()
                shoppingListAdapter.submitList(it)
                it.forEach{
                    item -> sum += item.price * item.amount
                    println(sum)
                }
                val totalText = "$ ${"%.2f".format(sum)}"
                binding.currentListTotal.text = totalText
            }
        }
        binding.budgetAmountDisplay.text = "%.2f".format(tripAmount)

        binding.addButton.setOnClickListener{addItemDialog()}
    }

    fun addItemDialog(){
        val builder = AlertDialog.Builder(this.context)
        val input = EditText(this.context)

        builder.setTitle("Add new item")
            .setView(input)
            .setPositiveButton("Add") {_,_ ->
                // add the function to add the item to that database
                val inputedText = input.text.toString()
                println(inputedText)
                val item = ShoppingList(id= UUID.randomUUID(), inputedText, 1, 0.00.toFloat(), tripId )
                lifecycleScope.launch(Dispatchers.Default) {
                    viewModel.upsert(item)
                }
                Toast.makeText(this.context, "Item added to list", Toast.LENGTH_SHORT).show()
            }
            .create()
        builder.show()
    }
}
