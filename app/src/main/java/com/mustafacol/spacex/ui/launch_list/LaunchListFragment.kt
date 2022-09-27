package com.mustafacol.spacex.ui.launch_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafacol.spacex.MainActivity
import com.mustafacol.spacex.adapter.LaunchesAdapter
import com.mustafacol.spacex.data.LaunchItem
import com.mustafacol.spacex.databinding.FragmentLaunchListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchListFragment : Fragment() {

    private val viewmodel by viewModel<LaunchListViewModel>()
    private var _binding: FragmentLaunchListBinding? = null
    private val binding get() = _binding!!

    private var launchList = mutableListOf<LaunchItem>()
    private lateinit var adapter: LaunchesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLaunchListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        init()
        setupObservers()
        if (launchList.isEmpty())
            viewmodel.getLaunches()
        return view
    }

    private fun init() {
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setHomeButtonEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.title = "Launch List"
        adapter = LaunchesAdapter(launchList) {
            viewmodel.getNextLaunches()
        }
        binding.recyclerviewLaunches.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerviewLaunches.adapter = adapter
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewmodel.launchesState.collectLatest {
                when (it) {
                    is LaunchListViewModel.LaunchesViewState.Success -> {
                        (activity as MainActivity).setProgressBarVisibility(View.GONE)
                        launchList.addAll(it.launches)
                        adapter.notifyDataSetChanged()
                    }
                    is LaunchListViewModel.LaunchesViewState.Error -> {
                        (activity as MainActivity).setProgressBarVisibility(View.GONE)
                        Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
                    }

                    LaunchListViewModel.LaunchesViewState.Loading -> {
                        (activity as MainActivity).setProgressBarVisibility(View.VISIBLE)
                    }

                }
            }
        }
    }


}


