package com.hogehoge.activity_monitor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        // 1. オーバーレイ権限ボタン
        val btnOverlay = Button(this).apply {
            text = "1. 重ねて表示を許可"
            setOnClickListener {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivity(intent)
            }
        }

        // 2. ユーザー補助ボタン
        val btnAccess = Button(this).apply {
            text = "2. ユーザー補助をON"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }

        layout.addView(btnOverlay)
        layout.addView(btnAccess)
        setContentView(layout)
    }
}