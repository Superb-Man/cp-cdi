package com.example.demo2.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.demo2.R
import com.example.demo2.ViewModel.TimeViewModel
import com.example.demo2.databinding.FragmentTimeBinding

const val NAVIGATION_DELAY = 10000L // 10 seconds

const val SAVED_MILISC = "saved_milsec"

class TimeFragment : Fragment() {

    private lateinit var binding: FragmentTimeBinding
    private val viewModel: TimeViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())

    init {
        Log.d("Time Fragment", "Init called->new created")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTimeBinding.inflate(inflater, container, false)
        Log.d("Time Fragment", "onCreateView called")

        viewModel.updateTime(savedInstanceState?.getLong(SAVED_MILISC)) // Initialize ViewModel data

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Time Fragment", "onViewCreated called")
        super.onViewCreated(view, savedInstanceState)

        viewModel.time.observe(viewLifecycleOwner) { time ->
            binding.textTime.text = time.toString()
        }


        binding.btnGoToDate.setOnClickListener {
            viewModel.storeCurrentTime()
            findNavController().navigate(R.id.action_timeFragment_to_dateFragment)
        }

        binding.buttonCnt.setOnClickListener {
            viewModel.clearDB()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }




    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("Time Fragment", "onSaveInstanceState called")
        outState.putLong(SAVED_MILISC, viewModel.time.value ?: System.currentTimeMillis()) // Preserve timestamp
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        Log.d("Time Fragment", "onStart called")
        super.onStart()
    }

    override fun onResume() {
        Log.d("Time Fragment", "onResume called")
        super.onResume()
        handler.postDelayed({
            if (isAdded) { // Ensure fragment is still attached
                findNavController().navigate(R.id.action_timeFragment_to_dateFragment)
            }
        }, NAVIGATION_DELAY)
    }

    override fun onPause() {
        Log.d("Time Fragment", "onPause called")
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onStop() {
        Log.d("Time Fragment", "onStop called")
        super.onStop()
    }
}



