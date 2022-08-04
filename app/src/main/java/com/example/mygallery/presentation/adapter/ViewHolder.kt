package com.example.mygallery.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.mygallery.R
import com.example.mygallery.databinding.PictureItemBinding
import com.example.mygallery.domain.Picture
import com.example.mygallery.utilis.loadImage

class ViewHolder(
    private val binding: PictureItemBinding,
    private val onFeaturedClick: (Picture) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(picture: Picture) {
        binding.ivPhoto.loadImage(picture.downloadUrl)
        if (picture.favorite)
            binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_rate_24)
        else
            binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24)

        binding.imageButton.setOnClickListener {
            if (picture.favorite) {
                binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
            } else {
                binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_rate_24)
            }
            onFeaturedClick(picture)
        }
    }
}