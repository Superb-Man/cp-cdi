package com.example.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator

class MainActivity : AppCompatActivity() {
    private lateinit var simulationLayout: RelativeLayout
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var button: Button
    private lateinit var ball: ImageView
    private var dx = 10
    private var dy = 10
    private var score = 0
    private var isFragmentVisible = false
    private var animator: ValueAnimator? = null
    private var firstFragment: FirstFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simulationLayout = findViewById(R.id.simulationLayout)
        fragmentContainer = findViewById(R.id.fragmentContainer)
        button = findViewById(R.id.button)
        ball = findViewById(R.id.ball)
        ball.setImageResource(R.drawable.ball)

        simulationLayout.post {
            startBallAnimation()
        }

        button.setOnClickListener {
            if (isFragmentVisible) {
                supportFragmentManager.popBackStack()
                button.text = "Press me!"
                ball.visibility = View.VISIBLE
                simulationLayout.visibility = View.VISIBLE
            } else {
                firstFragment = FirstFragment(score)
                loadFragment(firstFragment!!)
                button.text = "Go Back"
                ball.visibility = View.INVISIBLE
                simulationLayout.visibility = View.INVISIBLE
            }
            isFragmentVisible = !isFragmentVisible
        }
    }

    private fun startBallAnimation() {
        animator = ValueAnimator.ofFloat(0f, 1f)
        animator?.duration = 16L
        animator?.repeatCount = ValueAnimator.INFINITE
        animator?.interpolator = LinearInterpolator()

        animator?.addUpdateListener {
            val layoutParams = ball.layoutParams as RelativeLayout.LayoutParams
            val maxWidth = simulationLayout.width - ball.width
            val maxHeight = simulationLayout.height - ball.height

            layoutParams.leftMargin = (layoutParams.leftMargin + dx).coerceIn(0, maxWidth)
            layoutParams.topMargin = (layoutParams.topMargin + dy).coerceIn(0, maxHeight)

            if (layoutParams.leftMargin <= 0 || layoutParams.leftMargin >= maxWidth) {
                dx *= -1
                score++
            }
            if (layoutParams.topMargin <= 0 || layoutParams.topMargin >= maxHeight) {
                dy *= -1
                score++
            }

            ball.layoutParams = layoutParams


            firstFragment?.updateScore(score)
        }

        animator?.start()
    }

    private fun loadFragment(fragment: Fragment) {
        fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
        }
    }
}
