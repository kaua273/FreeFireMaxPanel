
package com.panel

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import android.graphics.Typeface

class MainActivity : Activity() {

    private val red = Color.rgb(255, 35, 55)
    private val white = Color.WHITE
    private val dark = Color.rgb(15, 15, 20)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 28, 22, 22)
            setBackgroundColor(dark)
        }

        val scroll = ScrollView(this)
        scroll.addView(root)

        val title = TextView(this).apply {
            text = "FREE FIRE MAX"
            textSize = 27f
            setTextColor(red)
            gravity = Gravity.CENTER
            setTypeface(null, Typeface.BOLD)
        }
        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "PANEL • DEMONSTRAÇÃO VISUAL"
            textSize = 12f
            setTextColor(white)
            gravity = Gravity.CENTER
        }
        root.addView(subtitle)

        addSpace(root, 25)

        addSwitch(root, "AIMBOT • DEMO")
        addSwitch(root, "ESP • DEMO")
        addSwitch(root, "NO RECOIL • DEMO")

        addSpace(root, 20)

        val fovTitle = TextView(this).apply {
            text = "FOV: 90"
            textSize = 18f
            setTextColor(white)
        }
        root.addView(fovTitle)

        val fov = SeekBar(this).apply {
            max = 180
            progress = 90
            progressTintList =
                android.content.res.ColorStateList.valueOf(red)
            thumbTintList =
                android.content.res.ColorStateList.valueOf(red)
        }

        fov.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    bar: SeekBar?,
                    value: Int,
                    fromUser: Boolean
                ) {
                    fovTitle.text = "FOV: $value"
                }

                override fun onStartTrackingTouch(bar: SeekBar?) {}
                override fun onStopTrackingTouch(bar: SeekBar?) {}
            }
        )

        root.addView(fov)

        addSpace(root, 25)

        val info = TextView(this).apply {
            text = "STATUS: DEMONSTRAÇÃO ATIVA\n\nEste painel é independente e não modifica nem controla jogos."
            textSize = 14f
            setTextColor(red)
            gravity = Gravity.CENTER
            setPadding(12, 20, 12, 20)
        }

        root.addView(info)

        setContentView(scroll)
    }

    private fun addSwitch(root: LinearLayout, name: String) {
        val control = Switch(this).apply {
            text = name
            textSize = 16f
            setTextColor(white)
            setPadding(5, 15, 5, 15)
            buttonTintList =
                android.content.res.ColorStateList.valueOf(red)
        }

        root.addView(control)
    }

    private fun addSpace(root: LinearLayout, height: Int) {
        val space = TextView(this)
        root.addView(
            space,
            LinearLayout.LayoutParams(
                1,
                (height * resources.displayMetrics.density).toInt()
            )
        )
    }
}
private fun abrirFreeFire() {
    val intent = packageManager
        .getLaunchIntentForPackage("com.dts.freefiremax")

    if (intent != null) {
        startActivity(intent)
    } else {
        Toast.makeText(
            this,
            "Free Fire MAX não encontrado!",
            Toast.LENGTH_LONG
        ).show()
    }
}
