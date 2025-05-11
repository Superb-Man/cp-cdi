package com.example.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FirstFragment(private var initialScore: Int) : Fragment() {
    private lateinit var scoreText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_score, container, false)
        scoreText = view.findViewById(R.id.scoreText)
        scoreText.text = "Score: $initialScore"
        return view
    }

    fun updateScore(newScore: Int) {
        activity?.runOnUiThread {
            scoreText.text = "Score: $newScore"
        }
    }
}
