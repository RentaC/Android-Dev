package com.example.getadog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DogAdapter : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    private var dogs: List<Dog> = emptyList()
    private var onItemClick: ((Dog) -> Unit)? = null

    fun updateData(newDogs: List<Dog>) {
        dogs = newDogs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = dogs[position]
        holder.bind(dog)
    }

    override fun getItemCount(): Int {
        return dogs.size
    }

    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val breedTextView: TextView = itemView.findViewById(R.id.breedTextView)

        fun bind(dog: Dog) {
            breedTextView.text = dog.breed
            itemView.setOnClickListener {
                // Handle item click
                //onItemClick(dog)
                onItemClick?.invoke(dog)
            }
        }

    }

    fun setOnItemClickListener(listener: (Dog) -> Unit) {
        onItemClick = listener
    }
}
