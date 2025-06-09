package com.example.demo2.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo2.Adapter.HealthCardAdapter
import com.example.demo2.R
import com.example.demo2.ViewModel.HealthCardsViewModel
import com.example.demo2.databinding.FragmentHealthCardsBinding
import kotlinx.coroutines.launch

class HealthCardsFragment : Fragment() {
    private lateinit var binding: FragmentHealthCardsBinding
    private val viewModel: HealthCardsViewModel by viewModels()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHealthCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TAG", "onViewCreated: ")
        lifecycleScope.launch {
            binding.recyclerView2.adapter =
                HealthCardAdapter(viewModel.getAllHealthData(), viewModel)
        }


        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView2.setHasFixedSize(true)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view.findNavController().popBackStack()
        }


    }
}
