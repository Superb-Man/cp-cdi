package com.example.demo2.Fragment

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.demo2.R
import com.example.demo2.databinding.FragmentHelloBinding

class HelloFragment : Fragment() {
    private lateinit var binding: FragmentHelloBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentHelloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view.findNavController().popBackStack()
        }

        binding.btnGoBack.setOnClickListener {
            view.findNavController().navigate(R.id.action_helloFragment_to_timeFragment)
        }
    }
}
