package com.example.demo2.Fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.demo2.R

import com.example.demo2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.d("Time Fragment", "onCreateView called")
        requireActivity().title = getString(R.string.home_page)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("Home Fragment", "onViewCreated called")
        binding.energyScoreCard.setOnClickListener {
            Log.d("Time Fragment", "Energy Score Card clicked")
        }
    }
}