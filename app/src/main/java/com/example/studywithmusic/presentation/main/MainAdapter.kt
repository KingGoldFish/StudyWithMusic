package com.example.studywithmusic.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studywithmusic.R
import com.example.studywithmusic.presentation.model.PresentationStorageItem

class MainAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val items = mutableListOf<PresentationStorageItem>()

    interface OnItemClickListener {
        fun onFolderClick(item: PresentationStorageItem)
        fun onFileClick(item: PresentationStorageItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<PresentationStorageItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewItemName)

        fun bind(item: PresentationStorageItem) {
            titleTextView.text = item.displayName
            itemView.setOnClickListener {
                if (item.isFolder) {
                    listener.onFolderClick(item)
                } else {
                    listener.onFileClick(item)
                }
            }
        }
    }
}