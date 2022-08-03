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
        when (picture.favorite) {
            false -> {
                binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
            }
            true -> {
                binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_rate_24)
            }
        }

        binding.imageButton.setOnClickListener {
            when (picture.favorite) {
                true -> {
                    binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
                }
                false -> {
                    binding.imageButton
                        .setBackgroundResource(R.drawable.ic_baseline_star_rate_24)
                }
            }
            onFeaturedClick(picture)
        }
    }
}