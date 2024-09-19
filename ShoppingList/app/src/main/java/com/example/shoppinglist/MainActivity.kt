package com.example.shoppinglist

import android.app.AlertDialog
import android.graphics.Paint
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var itemList: MutableList<Item>
    private lateinit var adapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addItemEditText: EditText
    //private var edited = false

   // data class Item(var text: String, var checked: Boolean = false)
   data class Item(var text: String, var checked: Boolean = false, var selected: Boolean = false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemList = mutableListOf()
        adapter = ItemAdapter(itemList)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addItemEditText = findViewById(R.id.addItemEditText)

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            addItem()
        }

        val deleteAllButton: Button = findViewById(R.id.deleteAllButton)
        deleteAllButton.setOnClickListener {
            if (itemList.isNotEmpty()) {
                showDeleteConfirmationDialog()
            }
        }
    }

    private fun addItem() {
        val itemText = addItemEditText.text.toString().trim()
        if (itemText.isNotEmpty()) {
            itemList.add(Item(itemText))                    //, checkedPosition = itemList.size))
            adapter.notifyItemInserted(itemList.size - 1)
            addItemEditText.text.clear()
        } else {
            Toast.makeText(this, "Please enter an item", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All Items")
        builder.setMessage("Are you sure you want to delete all items?")
        builder.setPositiveButton("Yes") { _, _ ->
            itemList.clear()
            adapter.notifyDataSetChanged()
        }
        builder.setNegativeButton("No", null)
        val dialog = builder.create()
        dialog.show()
    }

    inner class ItemAdapter(private val items: MutableList<Item>) :
        RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemTextView: TextView = itemView.findViewById(R.id.itemTextView)
            val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
            val editButton: Button = itemView.findViewById(R.id.editButton)
            val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row, parent, false)
            return ItemViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val currentItem = items[position]

            //holder.itemTextView.text = "${position + 1}. ${currentItem.text}"         //currentItem.text

            val itemText = holder.itemView.context.getString(R.string.item_format, position + 1, currentItem.text)
            holder.itemTextView.text = itemText

            updateItemTextView(holder.itemTextView, currentItem.checked)

            holder.checkBox.setOnCheckedChangeListener(null)
            holder.checkBox.isChecked = currentItem.checked

            //Define CheckBox's selected according to the value of Item for selected
            holder.checkBox.isSelected = currentItem.selected

            holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
                currentItem.checked = isChecked
                updateItemTextView(holder.itemTextView, currentItem.checked)

            }

            holder.checkBox.setOnClickListener {
                // invert the value of the filed selected from Item
                currentItem.selected = !currentItem.selected

                holder.checkBox.isSelected = currentItem.selected
            }

            // Apply the necessary updates for the appearance of TextView and CheckBox
            updateItemTextView(holder.itemTextView, currentItem.checked)
            updateCheckBoxAppearance(holder.checkBox, currentItem.selected)

            holder.editButton.setOnClickListener {
                showEditDialog(position)
            }

            holder.deleteButton.setOnClickListener {
                val itemPosition = holder.adapterPosition
                itemList.removeAt(itemPosition)

//                itemList.add(itemPosition + 1, currentItem.copy(checkedPosition = itemPosition))

//                itemList.add(itemPosition - 1 , currentItem.copy(checkedPosition = itemPosition))

                notifyItemRemoved(itemPosition)
                notifyItemRangeChanged(itemPosition, items.size - itemPosition)

                if (itemList.size > itemPosition) {
                    val nextItem = itemList[itemPosition]
                    nextItem.checked = false
                    notifyItemChanged(itemPosition)
                }
            }
        }

        private fun updateCheckBoxAppearance(checkBox: CheckBox, selected: Boolean) {
            checkBox.isChecked = selected
            checkBox.paintFlags = if (selected) {
                checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        override fun getItemCount(): Int {
            return items.size
        }

        private fun showEditDialog(position: Int) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Edit Item")
            val input = EditText(this@MainActivity)
            input.setText(itemList[position].text)
            input.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(100)) // Set maximum input length
            builder.setView(input)
            builder.setPositiveButton("Save") { _, _ ->
                //edited = true                                   // <===================================================
                val newText = input.text.toString().trim()
                if (newText.isNotEmpty()) {
                    if (newText != itemList[position].text) {
                        itemList[position].text = newText
                        adapter.notifyItemChanged(position)
                        updateItemTextView(adapter.getItemTextView(position), itemList[position].checked)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Please enter an item", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        }


        private fun updateItemTextView(textView: TextView, checked: Boolean) {
            if (checked) {
                textView.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    alpha = 0.5f
                }
            } else {
                textView.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    alpha = 1f
                }
            }
        }

        private fun getItemTextView(position: Int): TextView {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            return viewHolder?.itemView?.findViewById(R.id.itemTextView) as TextView
        }
    }
}
