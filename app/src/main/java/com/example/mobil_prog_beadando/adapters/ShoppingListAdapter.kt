package com.example.mobil_prog_beadando.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobil_prog_beadando.databinding.ShoppingItemBinding

import com.example.mobil_prog_beadando.database.shoppinglist.ShoppingList
import com.example.mobil_prog_beadando.viewmodels.ShoppingListViewModel
import kotlinx.coroutines.runBlocking
import java.util.*


class ShoppingListAdapter(
    private val viewModel: ShoppingListViewModel
): ListAdapter<ShoppingList, ShoppingListAdapter.ShoppingListViewHolder>(DiffCallback) {

    class ShoppingListViewHolder(var binding: ShoppingItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(shoppingList: ShoppingList){
            binding.shoppingItemName.text = shoppingList.name
            binding.shoppingItemAmount.text = shoppingList.amount.toString()
            binding.shoppingItemPrice.hint = "%.2f".format(shoppingList.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val viewHolder = ShoppingListViewHolder(
            ShoppingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        var item = getItem(position)
        holder.binding.addAmountButton.setOnClickListener {
            var amount = holder.binding.shoppingItemAmount.text.toString().toInt()
            amount++
            holder.binding.shoppingItemAmount.text = amount.toString()

            val body  = ShoppingList(item.id, item.name, amount, item.price, item.tripId)
            runBlocking {
                viewModel.upsert(body)
            }
        }

        holder.binding.minusAmountButton.setOnClickListener {
            var amount = holder.binding.shoppingItemAmount.text.toString().toInt()
            amount--
            holder.binding.shoppingItemAmount.text = amount.toString()
            val body  = ShoppingList(item.id, item.name, amount, item.price, item.tripId)
            runBlocking {
                viewModel.upsert(body)
            }
        }

        holder.binding.deleteItemButton.setOnClickListener{
            runBlocking {
                viewModel.deleteItem(item)
            }
        }

        holder.binding.shoppingItemPrice.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val text = holder.binding.shoppingItemPrice.text
                var timer = Timer()
                timer.schedule(
                    object: TimerTask() {
                        override fun run(){
                            runBlocking {
                                viewModel.upsert(ShoppingList(item.id, item.name, item.amount, text.toString().toFloat(), item.tripId  ))
                            }
                        }
                    },
                   3000
                )
            }
        })
        holder.bind(getItem(position))
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<ShoppingList>(){
            override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
                return oldItem == newItem
            }
        }
    }
}
