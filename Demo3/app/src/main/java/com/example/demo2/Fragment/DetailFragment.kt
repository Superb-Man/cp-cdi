package com.example.demo2.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import androidx.activity.addCallback
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.demo2.Models.HealthData
import com.example.demo2.R
import com.example.demo2.ViewModel.DetailViewModel
import com.example.demo2.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        requireActivity().title = getString(R.string.details_page)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHealthData(args.id)
        viewModel.healthData.observe(viewLifecycleOwner) { healthData ->
            healthData?.let { bindHealthData(it) }
        }

        binding.editButton.setOnClickListener {
            Log.d("Detail Fragment","Edit button clicked!")
            // instead of sending an id send the selected data object as args.
            // db fetching redundancy will be less
            val action = DetailFragmentDirections.actionDetailFragmentToEditInputFragment(args.id)
            view.findNavController().navigate(action)
        }



        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view.findNavController().popBackStack()
        }
    }

    private fun bindHealthData(healthData: HealthData) {
        val diseasesList = mutableListOf<String>()

        if (healthData.disease1) diseasesList.add(getString(R.string.fatigue))
        if (healthData.disease2) diseasesList.add(getString(R.string.dizziness))
        if (healthData.disease3) diseasesList.add(getString(R.string.chest_pain))

        binding.dateText.apply {
            text = getString(R.string.date_label, healthData.date)
            contentDescription = getString(R.string.content_desc_date, healthData.date)
        }

        binding.timeText.apply {
            text = getString(R.string.time_label, healthData.time)
            contentDescription = getString(R.string.content_desc_time, healthData.time)
        }

        binding.activityText.apply {
            val activityText = healthData.activity ?: getString(R.string.none)
            text = getString(R.string.activity_label, activityText)
            contentDescription = getString(R.string.content_desc_activity, activityText)
        }

        binding.sugarLevelText.apply {
            text = getString(R.string.sugar_label, healthData.sugarLevel)
            contentDescription = getString(R.string.content_desc_sugar, healthData.sugarLevel)
        }

        binding.sleepDurationText.apply {
            text = getString(R.string.sleep_label, healthData.sleepDuration)
            contentDescription = getString(R.string.content_desc_sleep, healthData.sleepDuration)
        }

        binding.diseasesText.apply {
            if (diseasesList.isNotEmpty()) {
                text = getString(R.string.conditions_label, diseasesList.joinToString(", "))
                contentDescription = getString(R.string.content_desc_conditions, diseasesList.joinToString(", "))
            }
            else {
                text = getString(R.string.no_diseases)
                contentDescription = getString(R.string.content_desc_no_conditions)
            }
        }
    }


}
