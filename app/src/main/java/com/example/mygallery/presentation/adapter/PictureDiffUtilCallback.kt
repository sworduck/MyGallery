package com.example.mygallery.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mygallery.domain.Picture

class PictureDiffUtilCallback : DiffUtil.ItemCallback<Picture>() {
    override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
        return oldItem == newItem
    }
}