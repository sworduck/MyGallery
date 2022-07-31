package com.example.mygallery.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygallery.databinding.PictureItemBinding
import com.example.mygallery.domain.Picture
import com.example.mygallery.utilis.loadImage

class FragmentAdapter(): PagingDataAdapter<Picture, FragmentAdapter.ViewHolder>(PictureDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PictureItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item) }
    }

    class ViewHolder(private val binding: PictureItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(picture: Picture){
            binding.ivPhoto.loadImage(picture.downloadUrl)
        }
    }
}