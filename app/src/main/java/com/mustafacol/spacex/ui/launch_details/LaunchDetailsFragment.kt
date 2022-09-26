package com.mustafacol.spacex.ui.launch_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mustafacol.spacex.MainActivity
import com.mustafacol.spacex.R
import com.mustafacol.spacex.adapter.SliderAdapter
import com.mustafacol.spacex.data.LaunchDetailsItem
import com.mustafacol.spacex.databinding.FragmentLaunchDetailsBinding
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchDetailsFragment : Fragment() {

    private val viewmodel by viewModel<LaunchDetailsViewModel>()
    private var _binding: FragmentLaunchDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: LaunchDetailsFragmentArgs by navArgs()
    private lateinit var launchId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLaunchDetailsBinding.inflate(layoutInflater, container, false)
        launchId = args.launchId
        val view = binding.root

        setupObservers()
        viewmodel.getLaunchDetails(launchId)

        return view
    }

    private fun init(launchDetailsItem: LaunchDetailsItem) {

        setImageSlider(launchDetailsItem)

        binding.textviewMissionName.text = launchDetailsItem.missionName
        binding.textviewLaunchDate.text = launchDetailsItem.launchDateLocal
        binding.textviewRocketName.text = launchDetailsItem.rocket?.rocketName
        binding.textviewRocketType.text = launchDetailsItem.rocket?.rocketType
        binding.imageviewSuccessfulLaunch.apply {
            setImageResource(
                if (launchDetailsItem.launchSuccess == true) {
                    R.drawable.check_circle
                } else {
                    R.drawable.cancel
                }
            )
        }
        binding.textviewDescription.text = launchDetailsItem.details
        binding.textviewShips.text =
            if (launchDetailsItem.ships.isNotEmpty())
                launchDetailsItem.ships.joinToString(",")
            else
                "N/A"


    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewmodel.launchDetailsViewState.collect {
                when (it) {
                    is LaunchDetailsViewModel.LaunchDetailsViewState.Success -> {
                        (activity as MainActivity).setProgressBarVisibility(View.GONE)
                        init(it.launchDetailsItem)
                        setPageVisibility(View.VISIBLE)
                    }
                    is LaunchDetailsViewModel.LaunchDetailsViewState.Error -> {
                        (activity as MainActivity).setProgressBarVisibility(View.GONE)
                        Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
                        setPageVisibility(View.GONE)
                    }
                    LaunchDetailsViewModel.LaunchDetailsViewState.Loading -> {
                        (activity as MainActivity).setProgressBarVisibility(View.VISIBLE)
                        setPageVisibility(View.GONE)
                    }
                }
            }
        }
    }

    private fun setImageSlider(launchDetailsItem: LaunchDetailsItem) {
        val imageList = launchDetailsItem.links?.flickrImages ?: listOf()
        if (imageList.isEmpty()) {
            binding.slider.visibility = View.GONE
            setGuideLinePercentage(0.05f)
            return
        }
        val sliderAdapter = SliderAdapter(imageList)
        binding.slider.apply {
            setSliderAdapter(sliderAdapter)
            autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            scrollTimeInSec = 4
            isAutoCycle = true
            startAutoCycle()
        }
    }

    private fun setPageVisibility(visibility: Int) {
        binding.slider.visibility = visibility
        binding.cardDetails.visibility = visibility
    }

    private fun setGuideLinePercentage(percentage: Float) {
        val params =
            binding.detailsGuidelineHorizontal.layoutParams as ConstraintLayout.LayoutParams
        params.guidePercent = percentage
        binding.detailsGuidelineHorizontal.layoutParams = params
    }

}