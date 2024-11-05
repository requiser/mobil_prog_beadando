package com.example.mobil_prog_beadando.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobil_prog_beadando.fragments.TripFragmentDirections
import com.example.mobil_prog_beadando.databinding.TripButtonBinding
import com.example.mobil_prog_beadando.database.trip.Trip
import com.example.mobil_prog_beadando.viewmodels.TripViewModel
import kotlinx.coroutines.runBlocking

class TripAdapter (
    private val viewModel: TripViewModel
): ListAdapter<Trip, TripAdapter.TripViewHolder>(DiffCallback) {

    class TripViewHolder(var binding: TripButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Trip) {
            binding.tripButton.text = trip.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val viewHolder = TripViewHolder(
            TripButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val item: Trip = getItem(position)
        holder.binding.tripButton.setOnClickListener{
            val action = TripFragmentDirections.actionTripFragmentToShoppingListFragment(item.id.toString(), item.goal)
            holder.itemView.findNavController().navigate(action)
        }

        holder.binding.deleteButton.setOnClickListener{
            runBlocking {
                viewModel.deleteTrip(item)
            }
        }
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Trip>() {
            override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean {
                return oldItem == newItem
            }
        }
    }
}
