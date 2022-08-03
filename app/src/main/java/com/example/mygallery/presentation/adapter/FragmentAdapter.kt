package com.example.mygallery.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.mygallery.databinding.PictureItemBinding
import com.example.mygallery.domain.Picture

class FragmentAdapter(
    private val onFeaturedClick: (Picture) -> Unit,
) : PagingDataAdapter<Picture, ViewHolder>(PictureDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PictureItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false), onFeaturedClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item) }
    }
}