package com.hogehoge.activity_monitor

import android.accessibilityservice.AccessibilityService
import android.graphics.Color
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.TextView

class MyAccessibilityService : AccessibilityService() {

    private var windowManager: WindowManager? = null
    private var overlayTextView: TextView? = null

    override fun onServiceConnected() {
        super.onServiceConnected()
        showOverlay() // サービス開始時にオーバーレイを表示
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val className = event.className?.toString() ?: ""
            // オーバーレイのテキストを更新
            overlayTextView?.text = "Activity:\n$className"
        }
    }

    private fun showOverlay() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        overlayTextView = TextView(this).apply {
            setBackgroundColor(Color.argb(150, 0, 0, 0)) // 半透明の黒
            setTextColor(Color.WHITE)
            textSize = 12f
            setPadding(10, 10, 10, 10)
            text = "Waiting for Activity..."
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY, // ユーザー補助専用のレイヤー
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START // 左上に表示
            x = 50
            y = 100
        }

        windowManager?.addView(overlayTextView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (overlayTextView != null) windowManager?.removeView(overlayTextView)
    }

    override fun onInterrupt() {}
}