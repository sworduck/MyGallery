package com.example.mygallery.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygallery.databinding.LoadingStateBinding

class SearchLoadStateAdapter(private val retry: () -> Unit):LoadStateAdapter<SearchLoadStateAdapter.LoadStateVH>() {

    override fun onBindViewHolder(holder: LoadStateVH, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateVH {
        return LoadStateVH(LoadingStateBinding.inflate(LayoutInflater.from(parent.context),
            parent,false),retry)
    }

    class LoadStateVH(private val binding:LoadingStateBinding,private val retry: () -> Unit):RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState) {
            binding.retryButton.isVisible = loadState !is LoadState.Loading
            binding.errorMessage.isVisible = loadState !is LoadState.Loading
            binding.progressBar.isVisible = loadState is LoadState.Loading

            binding.retryButton.setOnClickListener {
                retry()
            }
        }
    }
}