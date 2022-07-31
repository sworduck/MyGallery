package com.example.mygallery.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mygallery.databinding.FavoriteFragmentBinding
import com.example.mygallery.presentation.FragmentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val vm: FavoriteViewModel by viewModels()
    private val adapter: FragmentAdapter = FragmentAdapter()
    private lateinit var binding:FavoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FavoriteFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        binding.searchRecycler.adapter = adapter
        setupView()
    }

    private fun initObservers() {

    }

    private fun setupView() {
        lifecycleScope.launch {
            vm.pictureList.collectLatest { pagedData ->
                adapter.submitData(pagedData)
            }
        }
    }
}