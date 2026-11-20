
package com.panel

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(25, 30, 25, 30)
            setBackgroundColor(Color.rgb(15, 15, 20))
        }

        val title = TextView(this).apply {
            text = "FREE FIRE MAX PANEL"
            textSize = 25f
            setTextColor(Color.RED)
            gravity = Gravity.CENTER
        }

        layout.addView(title)

        fun addSwitch(name: String) {
            val control = Switch(this).apply {
                text = name
                textSize = 18f
                setTextColor(Color.WHITE)
                setPadding(10, 20, 10, 20)
            }
            layout.addView(control)
        }

        addSwitch("AIMBOT UI - Demo")
        addSwitch("ESP - Demo")
        addSwitch("NO RECOIL - Demo")

        val fovTitle = TextView(this).apply {
            text = "FOV"
            textSize = 18f
            setTextColor(Color.WHITE)
        }

        layout.addView(fovTitle)

        val fov = SeekBar(this).apply {
            max = 180
            progress = 90
        }

        layout.addView(fov)

        setContentView(layout)
    }
}
