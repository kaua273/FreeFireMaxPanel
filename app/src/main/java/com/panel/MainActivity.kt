package com.panel

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.view.Gravity

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tela = TextView(this)
        tela.text = "APLICATIVO ABERTO!"
        tela.textSize = 24f
        tela.gravity = Gravity.CENTER

        setContentView(tela)
    }
}
