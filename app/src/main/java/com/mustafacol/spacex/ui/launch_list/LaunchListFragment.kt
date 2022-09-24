package com.mustafacol.spacex.ui.launch_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mustafacol.spacex.R

class LaunchListFragment : Fragment() {

    companion object {
        fun newInstance() = LaunchListFragment()
    }

    private lateinit var viewModel: LaunchListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LaunchListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}