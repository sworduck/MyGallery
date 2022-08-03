package com.example.mygallery.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mygallery.databinding.FavoriteFragmentBinding
import com.example.mygallery.domain.Picture
import com.example.mygallery.presentation.adapter.FragmentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val vm: FavoriteViewModel by viewModels()
    private val favoriteAdapter: FragmentAdapter = FragmentAdapter(::onFeaturedClick)
    private lateinit var binding: FavoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
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
        setupView()
    }

    private fun initObservers() {

    }

    private fun initRecyclerView() {
        binding.searchRecycler.apply {
            adapter = favoriteAdapter
        }
    }

    private fun setupView() {
        vm.bindPaging(favoriteAdapter)
    }

    private fun onFeaturedClick(picture: Picture) {
        when (picture.favorite) {
            true -> {
                vm.remove(picture)
            }
        }
    }
}