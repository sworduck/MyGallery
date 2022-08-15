package com.example.mygallery.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.example.mygallery.databinding.SearchFragmentBinding
import com.example.mygallery.domain.Picture
import com.example.mygallery.presentation.adapter.FragmentAdapter
import com.example.mygallery.presentation.adapter.SearchLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val vm: SearchViewModel by viewModels()
    private val searchAdapter: FragmentAdapter = FragmentAdapter(::onFeaturedClick)
    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        initRecyclerView()
        binding.mainRetryButton.setOnClickListener {
            setupView()
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            searchAdapter.loadStateFlow
                .collectLatest { loadState ->
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            binding.searchRecycler.isVisible = true
                            binding.layoutErrorNetwork.isVisible = false
                        }
                        is LoadState.Error -> {
                            binding.searchRecycler.isVisible = false
                            binding.layoutErrorNetwork.isVisible = true
                        }
                    }
                }
        }
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            vm.pictureList.cachedIn(lifecycleScope)
                .collectLatest {
                    searchAdapter.submitData(it)
                }
        }
    }

    private fun initRecyclerView() {
        binding.searchRecycler.adapter = searchAdapter.withLoadStateFooter(
            footer = SearchLoadStateAdapter{ searchAdapter.retry() }
        )
    }

    private fun onFeaturedClick(picture: Picture) =
        if (picture.favorite) {
                picture.favorite = false
                vm.removePicture(picture)
            }
            else {
                picture.favorite = true
                vm.addPicture(picture)
            }
}