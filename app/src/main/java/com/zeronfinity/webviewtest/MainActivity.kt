package com.zeronfinity.webviewtest

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.BaseInputConnection
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    /**
     * MyInputConnection
     * BaseInputConnection configured to be editable
     */
    class MyInputConnection(targetView: View, fullEditor: Boolean) : BaseInputConnection(targetView, fullEditor) {
        private var _editable: SpannableStringBuilder? = null

        override fun getEditable(): Editable {
            if (_editable == null) {
                _editable = Editable.Factory.getInstance()
                        .newEditable("Placeholder") as SpannableStringBuilder
            }
            return _editable!!
        }

        override fun commitText(text: CharSequence, newCursorPosition: Int): Boolean {
            _editable!!.append(text)
            return true
        }

    }
    class NoSuggestionsWebView : WebView {
        constructor(context: Context) : super(context) {}
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
        constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

        override fun onCheckIsTextEditor(): Boolean {
            return true
        }

        override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
            outAttrs.inputType = outAttrs.inputType and EditorInfo.TYPE_MASK_VARIATION.inv() /* clear VARIATION type to be able to set new value */
            outAttrs.inputType = outAttrs.inputType or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD /* WEB_PASSWORD type will prevent form suggestions */
            return MyInputConnection(this, true)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customWebView = WebView(applicationContext)
        setContentView(customWebView)

        customWebView.visibility = View.VISIBLE
        customWebView.isFocusable = true
        customWebView.requestFocus()

        customWebView.settings.javaScriptEnabled = true
        customWebView.settings.domStorageEnabled = true

        customWebView.settings.loadsImagesAutomatically = true
        customWebView.settings.userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0"
        customWebView.settings.setGeolocationEnabled(true)

        customWebView.requestFocusFromTouch();

        customWebView.webViewClient = WebViewClient()

        customWebView.loadUrl("https://oos2019.hoxro.com/we/wordeditorframe.aspx?ui=en-US&rs=en-US&dchat=1&IsLicensedUser=1&WOPISrc=http%3A%2F%2Fwopi.hoxro.com%2Fwopi%2Ffiles%2FNGEzNDkyMWItMjEwZS00ZmMxLWI3ZWEtZTExYWJmZDZiZDA4LmRvY3g%3D&access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiJydmRlbW9AaG94cm8uY29tIiwidW5pcXVlX25hbWUiOiJSYWogVmlyZGVlIiwiZW1haWwiOiJydmRlbW9AaG94cm8uY29tIiwiRmlsZU9yaWdpbmFsTmFtZSI6IjQuIEluaXRpYWwgQ2xpZW50IExldHRlciBMZWFzZWhvbGQuZG9jeCIsIlVzZXJQZXJtaXNzaW9ucyI6IlVzZXJDYW5BdHRlbmQsIFVzZXJDYW5QcmVzZW50LCBVc2VyQ2FuUmVuYW1lLCBVc2VyQ2FuV3JpdGUiLCJXb3BpRG9jc1R5cGUiOiJXb3BpRG9jcyIsIklzVmVyc2lvbmFibGUiOiJUcnVlIiwibmJmIjoxNjMyNjMxODY5LCJleHAiOjE2MzI3MTgyNjksImlhdCI6MTYzMjYzMTg2OX0.bE0LaMbZ0zi5hlJ_Nsv2tunkWp26pHnxMEvtyWoopQg&access_token_ttl=1632718269540")
    }
}
