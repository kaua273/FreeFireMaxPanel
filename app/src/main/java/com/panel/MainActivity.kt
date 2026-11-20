
package com.panel

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.provider.Settings
import android.net.Uri
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.*

class MainActivity : Activity() {

    private var janela: LinearLayout? = null
    private lateinit var wm: WindowManager
    private lateinit var botao: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wm = getSystemService(WINDOW_SERVICE) as WindowManager

        val fundo = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(30, 40, 30, 40)
            setBackgroundColor(Color.rgb(15, 15, 20))
        }

        val titulo = TextView(this).apply {
            text = "FF MAX PANEL"
            textSize = 27f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(255, 45, 65))
        }

        val descricao = TextView(this).apply {
            text = "PAINEL FLUTUANTE INDEPENDENTE"
            textSize = 13f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setPadding(0, 15, 0, 25)
        }

        botao = Button(this).apply {
            text = "ATIVAR JANELA"
            setOnClickListener {
                abrirJanela()
            }
        }

        fundo.addView(titulo)
        fundo.addView(descricao)
        fundo.addView(botao)

        setContentView(fundo)
    }

    private fun abrirJanela() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
            return
        }

        if (janela != null) return

        val painel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 15, 22, 15)
            setBackgroundColor(Color.rgb(25, 20, 28))
        }

        val cabecalho = TextView(this).apply {
            text = "FF MAX PANEL  ⋮"
            textSize = 18f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(255, 50, 70))
            setPadding(8, 10, 8, 15)
        }

        val status = TextView(this).apply {
            text = "JANELA FLUTUANTE ATIVA"
            textSize = 12f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setPadding(8, 8, 8, 15)
        }

        val fechar = Button(this).apply {
            text = "FECHAR"
            setOnClickListener {
                fecharJanela()
            }
        }

        painel.addView(cabecalho)
        painel.addView(status)
        painel.addView(fechar)

        val parametros = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 30
            y = 150
        }

        cabecalho.setOnTouchListener { _, evento ->
            when (evento.action) {
                MotionEvent.ACTION_DOWN -> {
                    cabecalho.tag = floatArrayOf(
                        evento.rawX,
                        evento.rawY,
                        parametros.x.toFloat(),
                        parametros.y.toFloat()
                    )
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val pos = cabecalho.tag as FloatArray

                    parametros.x =
                        (pos[2] + evento.rawX - pos[0]).toInt()

                    parametros.y =
                        (pos[3] + evento.rawY - pos[1]).toInt()

                    wm.updateViewLayout(painel, parametros)
                    true
                }

                else -> false
            }
        }

        try {
            wm.addView(painel, parametros)
            janela = painel
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Erro ao abrir janela",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun fecharJanela() {
        janela?.let {
            try {
                wm.removeView(it)
            } catch (_: Exception) {
            }
        }
        janela = null
    }

    override fun onDestroy() {
        fecharJanela()
        super.onDestroy()
    }
}
