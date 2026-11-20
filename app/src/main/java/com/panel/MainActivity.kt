package com.panel

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val prefs by lazy {
        getSharedPreferences("infinity_settings", MODE_PRIVATE)
    }

    private var accent = Color.rgb(255, 35, 65)
    private val dark = Color.rgb(10, 11, 17)
    private val cardColor = Color.rgb(25, 26, 36)
    private val white = Color.WHITE

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accent = prefs.getInt("accent", Color.rgb(255, 35, 65))
        buildUI()
    }

    private fun buildUI() {
        val scroll = ScrollView(this)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(25), dp(18), dp(28))
            background = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(dark, Color.rgb(30, 10, 22), dark)
            )
        }

        scroll.addView(root)

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(10), dp(18), dp(10), dp(18))
            background = rounded(Color.rgb(28, 18, 29), 24, accent)
        }

        val logo = text("✦ INFINITY X", 29f, accent, true)
        logo.gravity = Gravity.CENTER
        header.addView(logo)

        val subtitle = text("FF MAX  •  ULTIMATE PANEL", 12f, white, true)
        subtitle.gravity = Gravity.CENTER
        header.addView(subtitle)

        val version = text("PREMIUM EDITION  |  V1.0", 10f,
            Color.LTGRAY, false)
        version.gravity = Gravity.CENTER
        header.addView(version)

        root.addView(header)
        space(root, 18)

        val status = text(
            "●  PAINEL INDEPENDENTE ATIVO",
            12f, accent, true
        )
        status.gravity = Gravity.CENTER
        root.addView(status)

        space(root, 20)
        section(root, "MÓDULOS PRINCIPAIS")

        addModule(
            root, "AIM", "Simulador visual de mira",
            "aim_enabled", "Sensibilidade demonstrativa",
            "aim_value", 100, 50
        )

        addModule(
            root, "ESP", "Demonstração de elementos visuais",
            "esp_enabled", "Intensidade visual",
            "esp_value", 100, 70
        )

        addModule(
            root, "NO RECOIL", "Simulador gráfico independente",
            "recoil_enabled", "Intensidade da simulação",
            "recoil_value", 100, 50
        )

        space(root, 12)
        section(root, "CAMPO DE VISÃO")

        val fovCard = makeCard()
        val fovValue = prefs.getInt("fov", 90)

        val fovLabel = text("FOV: $fovValue°", 20f, white, true)
        fovCard.addView(fovLabel)

        val fovInfo = text(
            "Ajuste visual demonstrativo de 1° até 180°",
            11f, Color.LTGRAY, false
        )
        fovCard.addView(fovInfo)

        addSlider(
            fovCard, "Campo de visão", "fov",
            180, 90, "°", 1
        ) { value ->
            fovLabel.text = "FOV: ${if (value == 0) 1 else value}°"
        }

        root.addView(fovCard)
        space(root, 20)

        section(root, "PERSONALIZAÇÃO")

        val themeCard = makeCard()
        themeCard.addView(
            text("ESCOLHA SEU TEMA", 15f, white, true)
        )

        val themeRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val themes = listOf(
            "RED" to Color.rgb(255, 35, 65),
            "PURPLE" to Color.rgb(165, 75, 255),
            "CYAN" to Color.rgb(0, 190, 230)
        )

        themes.forEach { (name, color) ->
            val button = Button(this).apply {
                text = name
                textSize = 11f
                setTextColor(white)
                background = rounded(color, 14, color)
                setOnClickListener {
                    prefs.edit().putInt("accent", color).apply()
                    accent = color
                    buildUI()
                }
            }

            val params = LinearLayout.LayoutParams(
                0, dp(46), 1f
            )
            params.setMargins(dp(4), dp(10), dp(4), dp(5))
            themeRow.addView(button, params)
        }

        themeCard.addView(themeRow)
        root.addView(themeCard)

        space(root, 20)
        section(root, "AÇÕES")

        val launch = makeButton("ABRIR FREE FIRE MAX")
        launch.setOnClickListener { abrirJogo() }
        root.addView(launch)

        space(root, 10)

        val reset = makeButton("RESTAURAR CONFIGURAÇÕES")
        reset.setOnClickListener {
            prefs.edit().clear().apply()
            accent = Color.rgb(255, 35, 65)
            buildUI()
            Toast.makeText(
                this, "Configurações restauradas!",
                Toast.LENGTH_SHORT
            ).show()
        }
        root.addView(reset)

        space(root, 20)

        val footer = text(
            "INFINITY X\nINTERFACE DEMONSTRATIVA INDEPENDENTE",
            11f, Color.LTGRAY, false
        )
        footer.gravity = Gravity.CENTER
        root.addView(footer)

        setContentView(scroll)
    }

    private fun addModule(
        root: LinearLayout,
        name: String,
        description: String,
        toggleKey: String,
        sliderName: String,
        sliderKey: String,
        max: Int,
        default: Int
    ) {
        val card = makeCard()

        val title = text(name, 20f, accent, true)
        card.addView(title)

        card.addView(text(description, 12f, Color.LTGRAY, false))

        val toggle = Switch(this).apply {
            text = "ATIVAR DEMONSTRAÇÃO"
            textSize = 12f
            setTextColor(white)
            isChecked = prefs.getBoolean(toggleKey, false)
            thumbTintList = ColorStateList.valueOf(accent)
            trackTintList = ColorStateList.valueOf(
                Color.argb(130, 255, 255, 255)
            )

            setOnCheckedChangeListener { _, checked ->
                prefs.edit().putBoolean(toggleKey, checked).apply()
            }
        }

        card.addView(toggle)

        addSlider(
            card, sliderName, sliderKey,
            max, default, "%", 0
        )

        root.addView(card)
    }

    private fun addSlider(
        card: LinearLayout,
        label: String,
        key: String,
        max: Int,
        default: Int,
        suffix: String,
        minimum: Int,
        onChange: ((Int) -> Unit)? = null
    ) {
        val current = prefs.getInt(key, default)

        val valueLabel = text(
            "$label: ${current.coerceAtLeast(minimum)}$suffix",
            13f, white, true
        )
        card.addView(valueLabel)

        val seek = SeekBar(this).apply {
            this.max = max - minimum
            progress = (current - minimum).coerceIn(0, this.max)
            progressTintList = ColorStateList.valueOf(accent)
            thumbTintList = ColorStateList.valueOf(accent)
        }

        seek.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    bar: SeekBar?, progress: Int, fromUser: Boolean
                ) {
                    val value = progress + minimum
                    valueLabel.text = "$label: $value$suffix"

                    if (fromUser) {
                        prefs.edit().putInt(key, value).apply()
                        onChange?.invoke(value)
                    }
                }

                override fun onStartTrackingTouch(bar: SeekBar?) {}
                override fun onStopTrackingTouch(bar: SeekBar?) {}
            }
        )

        card.addView(seek)
    }

    private fun makeCard(): LinearLayout {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(15), dp(16), dp(15))
            background = rounded(cardColor, 19, Color.rgb(55, 42, 58))
        }

        val params = LinearLayout.LayoutParams(-1, -2)
        params.bottomMargin = dp(13)
        return card.apply {
            layoutParams = params
        }
    }

    private fun section(root: LinearLayout, title: String) {
        root.addView(
            text(title, 14f, accent, true)
        )
        space(root, 10)
    }

    private fun makeButton(label: String): Button {
        return Button(this).apply {
            text = label
            textSize = 13f
            setTextColor(white)
            background = rounded(accent, 15, accent)
            isAllCaps = false
            layoutParams = LinearLayout.LayoutParams(
                -1, dp(52)
            ).apply {
                bottomMargin = dp(8)
            }
        }
    }

    private fun text(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(null, Typeface.BOLD)
            setPadding(0, dp(5), 0, dp(5))
        }
    }

    private fun rounded(
        color: Int,
        radius: Int,
        stroke: Int
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
            setStroke(dp(1), stroke)
        }
    }

    private fun space(root: LinearLayout, height: Int) {
        root.addView(
            View(this),
            LinearLayout.LayoutParams(1, dp(height))
        )
    }

    private fun abrirJogo() {
        val intent = packageManager.getLaunchIntentForPackage(
            "com.dts.freefiremax"
        )

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
}
