package com.mustafacol.spacex.ui.launch_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mustafacol.spacex.R

class LaunchDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = LaunchDetailsFragment()
    }

    private lateinit var viewModel: LaunchDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LaunchDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}