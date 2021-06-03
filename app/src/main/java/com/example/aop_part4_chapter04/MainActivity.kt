package com.example.aop_part4_chapter04

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.aop_part4_chapter04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var isGatheringAnimating : Boolean = false
    private var isCircleAnimating : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        initScrollViewListener()
        initMotionLayoutListener()
    }

    private fun initScrollViewListener() {
        mainBinding.scrollView.smoothScrollTo(0, 0)

        mainBinding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrolledValue = mainBinding.scrollView.scrollY

            if (scrolledValue > 150f.dpToPx(this@MainActivity).toInt()) {
                if (isGatheringAnimating.not()) {
//                    mainBinding.gatheringDigitalThingsBackgroundMotionLayout.transitionToEnd()
                    mainBinding.gatheringDigitalThingsLayout.transitionToEnd()
                    mainBinding.buttonShownMotionLayout.transitionToEnd()
                }
            } else {
                if (isGatheringAnimating.not()) {
//                    mainBinding.gatheringDigitalThingsBackgroundMotionLayout.transitionToStart()
                    mainBinding.gatheringDigitalThingsLayout.transitionToStart()
                    mainBinding.buttonShownMotionLayout.transitionToStart()
                }
            }

            if(scrolledValue > mainBinding.scrollView.height / 2) {
                if(!isCircleAnimating) {
                    mainBinding.circleViewMotionLayout.setTransition(R.id.circle_shown_start1, R.id.circle_shown_end1)
                    mainBinding.circleViewMotionLayout.transitionToEnd()
                    isCircleAnimating = true
                }
            }
        }
    }

    private fun initMotionLayoutListener() {
        mainBinding.gatheringDigitalThingsLayout.setTransitionListener(object: MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {
                //TODO: 전환을 시작하려고 할 때
                isGatheringAnimating = true
            }

            override fun onTransitionChange(p0: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                //TODO: 위치가 변경될 떄
            }

            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                //TODO: 완료됐을 때
                isGatheringAnimating = false

            }

            override fun onTransitionTrigger(p0: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {
                //TODO: 트리거가 실행될 때
            }
        })

        mainBinding.circleViewMotionLayout.setTransitionListener(object: MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                when(currentId) {
                    R.id.circle_shown_end1 -> {
                        mainBinding.circleViewMotionLayout.setTransition(R.id.circle_shown_start2, R.id.circle_shown_end2)
                        mainBinding.circleViewMotionLayout.transitionToEnd()
                    }
                }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }
        })
    }
}

fun Float.dpToPx(context: Context): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)