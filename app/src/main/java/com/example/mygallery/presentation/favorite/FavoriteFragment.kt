package com.example.mygallery.presentation.favorite

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
import androidx.paging.filter
import com.example.mygallery.databinding.FavoriteFragmentBinding
import com.example.mygallery.domain.Picture
import com.example.mygallery.presentation.adapter.FragmentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val vm: FavoriteViewModel by viewModels()
    private val favoriteAdapter: FragmentAdapter = FragmentAdapter(::onFeaturedClick)
    private lateinit var binding: FavoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            favoriteAdapter.loadStateFlow
                .collectLatest { loadState ->
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            binding.searchRecycler.isVisible = true
                            binding.layoutEmptyList.isVisible = false
                        }
                        is LoadState.Error -> {
                            binding.searchRecycler.isVisible = false
                            binding.layoutEmptyList.isVisible = true
                        }
                    }
                }
        }

        lifecycleScope.launch {
            vm.pictureList
                .cachedIn(lifecycleScope)
                .combine(vm.removedItemsFlow) { pagingData, removed ->
                    pagingData.filter {
                        it !in removed
                    }
                }
                .collectLatest {
                    favoriteAdapter.submitData(it)
                }
        }
    }


    private fun initRecyclerView() {
        binding.searchRecycler.adapter = favoriteAdapter
    }

    private fun onFeaturedClick(picture: Picture) {
        vm.remove(picture)
    }
}