
package com.panel

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.*
import android.graphics.drawable.GradientDrawable

class MainActivity : Activity() {

    private val red = Color.rgb(255, 35, 60)
    private val dark = Color.rgb(12, 12, 18)
    private val white = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 30, 22, 24)
            background = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(dark, Color.rgb(28, 10, 17))
            )
        }

        val scroll = ScrollView(this)
        scroll.addView(root)

        val title = TextView(this).apply {
            text = "FF MAX"
            textSize = 34f
            gravity = Gravity.CENTER
            setTextColor(red)
            setTypeface(null, Typeface.BOLD)
        }
        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "GAMING CONTROL PANEL"
            textSize = 12f
            gravity = Gravity.CENTER
            setTextColor(white)
        }
        root.addView(subtitle)

        space(root, 25)

        addCard(root, "AIMBOT", "Demonstração visual")
        addCard(root, "ESP", "Demonstração visual")
        addCard(root, "NO RECOIL", "Demonstração visual")

        space(root, 20)

        val fovLabel = TextView(this).apply {
            text = "FOV: 90"
            textSize = 18f
            setTextColor(white)
        }
        root.addView(fovLabel)

        val seek = SeekBar(this).apply {
            max = 180
            progress = 90
            progressTintList =
                android.content.res.ColorStateList.valueOf(red)
            thumbTintList =
                android.content.res.ColorStateList.valueOf(red)
        }

        seek.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    bar: SeekBar?, value: Int, user: Boolean
                ) {
                    fovLabel.text = "FOV: $value"
                }
                override fun onStartTrackingTouch(bar: SeekBar?) {}
                override fun onStopTrackingTouch(bar: SeekBar?) {}
            }
        )
        root.addView(seek)

        space(root, 25)

        val launch = Button(this).apply {
            text = "ABRIR FREE FIRE MAX"
            textSize = 16f
            setTextColor(white)
            setBackgroundColor(red)
            setOnClickListener { abrirJogo() }
        }
        root.addView(launch)

        space(root, 20)

        val status = TextView(this).apply {
            text = "STATUS: PAINEL ATIVO\nDEMONSTRAÇÃO INDEPENDENTE"
            gravity = Gravity.CENTER
            textSize = 13f
            setTextColor(red)
        }
        root.addView(status)

        setContentView(scroll)
    }

    private fun addCard(
        root: LinearLayout,
        name: String,
        description: String
    ) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 12, 16, 12)
            background = GradientDrawable().apply {
                setColor(Color.rgb(30, 25, 32))
                cornerRadius = 18f
                setStroke(2, red)
            }
        }

        val label = TextView(this).apply {
            text = name
            textSize = 17f
            setTextColor(white)
            setTypeface(null, Typeface.BOLD)
        }

        val detail = TextView(this).apply {
            text = description
            textSize = 12f
            setTextColor(Color.LTGRAY)
        }

        val toggle = Switch(this).apply {
            text = "Ativar demonstração"
            setTextColor(white)
            buttonTintList =
                android.content.res.ColorStateList.valueOf(red)
        }

        card.addView(label)
        card.addView(detail)
        card.addView(toggle)

        val params = LinearLayout.LayoutParams(
            -1, -2
        )
        params.bottomMargin = 16
        root.addView(card, params)
    }

    private fun abrirJogo() {
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

    private fun space(root: LinearLayout, height: Int) {
        root.addView(
            TextView(this),
            LinearLayout.LayoutParams(
                1,
                (height * resources.displayMetrics.density).toInt()
            )
        )
    }
}
