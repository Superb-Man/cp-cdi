package com.example.demo2.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo2.ViewModel.DateViewModel
import com.example.demo2.databinding.FragmentDateBinding


class DateFragment : Fragment() {
    private lateinit var binding: FragmentDateBinding
    private val viewModel: DateViewModel by viewModels() // Fragment normally uses viewmodel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        viewModel.timedatas?.observe(viewLifecycleOwner) { updatedList ->
            val recyclerViewState =
                binding.recyclerView.layoutManager?.onSaveInstanceState() // Save scroll position

            val adapter = binding.recyclerView.adapter as? TimestampAdapter
            if (adapter == null) {
                binding.recyclerView.adapter = updatedList?.let {
                    TimestampAdapter(it) { id, timestamp ->
                        viewModel.decreaseTimestamp(
                            id,
                            timestamp
                        )
                    }
                }
            } else {
                adapter.updateData(updatedList)
            }

            binding.recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState) // Restore scroll position
        }

        viewModel.show()


        binding.btnGoToTime.setOnClickListener {
            view.findNavController().popBackStack() // Navigate back
//            view.findNavController().navigate(R.id.action_dateFragment_to_timeFragment) // Navigate to TimeFragment
        }
        // on back press listener
        // go back to previous fragment

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view.findNavController().popBackStack() // Handle back press similarly
        }
    }

}
