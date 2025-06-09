package com.example.demo2.Fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.demo2.Models.HealthData
import com.example.demo2.R
import com.example.demo2.Utils.Utils
import com.example.demo2.ViewModel.EditViewModel
import com.example.demo2.databinding.FragmentInputBinding
import kotlinx.coroutines.launch
import java.util.*
import kotlin.getValue

class EditInputFragment : Fragment() {

    private lateinit var binding: FragmentInputBinding
    private val editViewModel: EditViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("EditInputFragment", "onCreateView: called")
        binding = FragmentInputBinding.inflate(inflater, container, false)
        requireActivity().title = getString(R.string.edit_page)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadHealthData(savedInstanceState)

        binding.datePicker.setOnClickListener {
            Utils.createDateBox(requireContext(), binding.datePicker)
        }

        binding.timePicker.setOnClickListener {
            Utils.createTimeBox(requireContext(), binding.timePicker)
        }

        binding.btnSubmit.setOnClickListener {
            val flag = Utils.validateFields(binding.sugarLevel,
                                                      binding.sleepDuration,
                                                      binding.activityLevelGroup)
            if (flag) {
                updateExistingData()
                Toast.makeText(context, "Data updated successfully!", Toast.LENGTH_LONG).show()
                view.findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view.findNavController().popBackStack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)


        val healthData = HealthData(
            id = args.id,
            date = binding.datePicker.text.toString(),
            time = binding.timePicker.text.toString(),
            sugarLevel = binding.sugarLevel.text.toString().toIntOrNull() ?: 0,
            sleepDuration = binding.sleepDuration.text.toString().toIntOrNull() ?: 0,
            activity = binding.activityLevelGroup
                .findViewById<RadioButton>(binding.activityLevelGroup.checkedRadioButtonId)?.text.toString(),
            disease1 = binding.checkFatigue.isChecked,
            disease2 = binding.checkDizziness.isChecked,
            disease3 = binding.checkChestPain.isChecked
        )


        val bundle = editViewModel.saveToBundle(healthData = healthData)

        outState.putAll(bundle)
    }




    private fun loadHealthData(savedInstanceState: Bundle?) {
        val healthDataId = args.id.toInt()

        /**
         * This is just a demo app
         * Instead of fetching same data everytime, use args to get the data from previous fragment
         */
        viewLifecycleOwner.lifecycleScope.launch {
            val healthData = editViewModel.loadFromBundle(savedInstanceState)?:editViewModel.getHealthData(healthDataId)
            Log.d("LifeCycle scope", "getHealthData: ${healthData.toString()}")

            populateFields(healthData)
        }
    }

    private fun populateFields(healthData: HealthData) {
        binding.datePicker.setText(healthData.date)
        binding.timePicker.setText(healthData.time)

        binding.sugarLevel.setText(healthData.sugarLevel.toString())
        binding.sleepDuration.setText(healthData.sleepDuration.toString())
        binding.activityLevelGroup.check(getRadioButtonId(healthData.activity.toString()))
        binding.checkFatigue.isChecked = healthData.disease1 == true
        binding.checkDizziness.isChecked = healthData.disease2 == true
        binding.checkChestPain.isChecked = healthData.disease3 == true
    }

    private fun updateExistingData() {
        val healthData = HealthData(
            id = args.id,
            date = binding.datePicker.text.toString(),
            time = binding.timePicker.text.toString(),
            sugarLevel = binding.sugarLevel.text.toString().toInt(),
            sleepDuration = binding.sleepDuration.text.toString().toInt(),
            activity = binding.activityLevelGroup
                .findViewById<RadioButton>(binding.activityLevelGroup.checkedRadioButtonId).text.toString(),
            disease1 = binding.checkFatigue.isChecked,
            disease2 = binding.checkDizziness.isChecked,
            disease3 = binding.checkChestPain.isChecked
        )



        editViewModel.updateHealthData(healthData = healthData)
    }




    private fun getRadioButtonId(activity: String): Int {
        return when (activity) {
            "Low" -> R.id.radio_low
            "Moderate" -> R.id.radio_moderate
            "High" -> R.id.radio_high
            else -> -1
        }
    }

}
