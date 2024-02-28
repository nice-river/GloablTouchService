package com.example.gloabaltouchservice

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.FrameLayout

class GlobalTouchService : AccessibilityService() {
    private var mLayout: FrameLayout? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }

    override fun onServiceConnected() {
        // Create an overlay and display the action bar
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        mLayout = FrameLayout(this)
        val lp = WindowManager.LayoutParams()
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        lp.format = PixelFormat.TRANSLUCENT
        lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.gravity = Gravity.TOP
        val inflater = LayoutInflater.from(this)
        inflater.inflate(R.layout.touch_detector, mLayout)
        wm.addView(mLayout, lp)

        configureStartButton()
        configTouchButton()
    }

    private fun configureStartButton() {
        val startButton = mLayout!!.findViewById<View>(R.id.start) as Button
        startButton.setOnClickListener {
            val screenButton =
                mLayout!!.findViewById<View>(R.id.touch) as Button
            screenButton.visibility = View.VISIBLE
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configTouchButton() {
        val touchButton = mLayout!!.findViewById<View>(R.id.touch) as Button
        touchButton.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Log.i(
                    "Touch",
                    String.format("x: %.4f, y: %.4f", event.x, event.y)
                )
            }
            true
        }

        touchButton.setOnClickListener {
            touchButton.visibility = View.INVISIBLE
        }
    }
}
