package com.midwestpilotcars.views.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.midwestpilotcars.R
import com.midwestpilotcars.helpers.CaptureSignatureView

class CaptureSignatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture_signature)

        val linearLayout = findViewById<LinearLayout>(R.id.signature_layout)
        var captureSignatureView = CaptureSignatureView(this, null)
        linearLayout.addView(captureSignatureView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

    }
}
