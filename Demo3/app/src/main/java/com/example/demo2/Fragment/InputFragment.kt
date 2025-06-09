package com.example.demo2.Fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.demo2.Models.HealthData
import com.example.demo2.R
import com.example.demo2.Utils.Utils
import com.example.demo2.ViewModel.InputViewModel
import com.example.demo2.databinding.FragmentInputBinding

open class InputFragment : Fragment() {

    private lateinit var binding: FragmentInputBinding
    private val viewModel: InputViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInputBinding.inflate(inflater, container, false)
        restoreData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.datePicker.setOnClickListener {
            Utils.createDateBox(requireContext(), binding.datePicker)
        }

        binding.timePicker.setOnClickListener {
            Utils.createTimeBox(requireContext(), binding.timePicker)
        }


        binding.btnSubmit.setOnClickListener{
            // collect the datas from elements
            val flag = Utils.validateFields(binding.sugarLevel,
                                                      binding.sleepDuration,
                                                      binding.activityLevelGroup)

            if (flag) {
                val healthData = HealthData(
                    date            = binding.datePicker.text.toString(),
                    time            = binding.timePicker.text.toString(),
                    sugarLevel      = binding.sugarLevel.text.toString().toInt(),
                    sleepDuration   = binding.sleepDuration.text.toString().toInt(),
                    activity        = binding.activityLevelGroup.findViewById<RadioButton>
                                    (binding.activityLevelGroup.checkedRadioButtonId).text.toString(),
                    disease1        = binding.checkFatigue.isChecked,
                    disease2        = binding.checkDizziness.isChecked,
                    disease3        = binding.checkChestPain.isChecked,
                    )
                viewModel.insert(healthData = healthData)
                Toast.makeText(context, "Data saved successfully!", Toast.LENGTH_LONG)
                    .show()
                clearData() // Clear form fields after submission
            }


        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view.findNavController().popBackStack() // Handle back press similarly
        }
    }

    override fun onResume() {
        super.onResume()
        restoreData()
    }

    override fun onPause() {
        super.onPause()
        saveFormDataToPrefs()
    }

    override fun onStop() {
        super.onStop()
        saveFormDataToPrefs()
    }



    private fun restoreData() {

        val savedDate = viewModel.getData("date") ?: Utils.getDefaultDate()
        val savedTime = viewModel.getData("time") ?: Utils.getDefaultTime()

        binding.datePicker.setText(savedDate)
        binding.timePicker.setText(savedTime)

        binding.sugarLevel.setText(viewModel.getData("sugarLevel") ?: "")
        binding.sleepDuration.setText(viewModel.getData("sleepDuration") ?: "")
        binding.activityLevelGroup.check(viewModel.getData("activity")?.toIntOrNull() ?: -1)

        binding.checkFatigue.isChecked = viewModel.getData("disease1") == "true"
        binding.checkDizziness.isChecked = viewModel.getData("disease2") == "true"
        binding.checkChestPain.isChecked = viewModel.getData("disease3") == "true"

    }

    private fun saveFormDataToPrefs() {
        viewModel.saveData("date", binding.datePicker.text.toString())
        viewModel.saveData("time", binding.timePicker.text.toString())
        viewModel.saveData("sugarLevel", binding.sugarLevel.text.toString())
        viewModel.saveData("sleepDuration", binding.sleepDuration.text.toString())
        viewModel.saveData("activity", binding.activityLevelGroup.checkedRadioButtonId.toString())

        viewModel.saveData("disease1", binding.checkFatigue.isChecked.toString())
        viewModel.saveData("disease2", binding.checkDizziness.isChecked.toString())
        viewModel.saveData("disease3", binding.checkChestPain.isChecked.toString())

    }

    private fun clearData() {
        binding.datePicker.setText(Utils.getDefaultDate())
        binding.timePicker.setText(Utils.getDefaultTime())
        binding.sugarLevel.text.clear()
        binding.sleepDuration.text.clear()
        binding.activityLevelGroup.clearCheck()
        binding.checkFatigue.isChecked   = false
        binding.checkDizziness.isChecked = false
        binding.checkChestPain.isChecked = false
    }


}
