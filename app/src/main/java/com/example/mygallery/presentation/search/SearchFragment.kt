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
import com.example.mygallery.databinding.SearchFragmentBinding
import com.example.mygallery.domain.Picture
import com.example.mygallery.domain.Status
import com.example.mygallery.domain.Status.*
import com.example.mygallery.presentation.adapter.FragmentAdapter
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
        setupView()
        binding.layoutErrorButtonRetry.setOnClickListener {
            vm.onClickRetryButton(searchAdapter)
        }
    }

    private fun initObservers() {
        vm.status.observe(viewLifecycleOwner){
            when(vm.status.value){
                is Fail -> {
                    print("fail")
                    binding.searchRecycler.isVisible = false
                    binding.layoutErrorNetwork.isVisible = true
                }
                is Success -> {
                    print("success")
                    binding.searchRecycler.isVisible = true
                    binding.layoutErrorNetwork.isVisible = false
                }
            }
        }
    }


    private fun setupView() {
        vm.bindPaging(searchAdapter)
    }


    private fun initRecyclerView() {
        binding.searchRecycler.apply {
            adapter = searchAdapter
        }
    }

    private fun onFeaturedClick(picture: Picture) {
        when (picture.favorite) {
            true -> {
                picture.favorite = false
                vm.removePicture(picture)
            }
            false -> {
                picture.favorite = true
                vm.addPicture(picture)
            }
        }
    }
}