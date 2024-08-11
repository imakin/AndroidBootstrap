package com.izzulmakin.androidbootstrap

/**
 *
 * /*******************************************************************************
 *  * This program and the accompanying materials are made available under the
 *  * terms of the Eclipse Public License 2.0 which is available at
 *  * http://www.eclipse.org/legal/epl-2.0.
 *  *
 *  * SPDX-License-Identifier: EPL-2.0
 *  *******************************************************************************/
 *
 * @purpose: The main app, make a webview and load the server and local host
 * @author: Muhammad Ramdan Izzulmakin
 * @date: 2024-August
 */
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.izzulmakin.androidbootstrap.ui.theme.AndroidBootstrapTheme

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.ui.viewinterop.AndroidView
import java.io.IOException
import java.net.ServerSocket


class MainActivity : ComponentActivity() {
    private final val taglog = "MMM========MMM"
    private lateinit var server: InternalServer
    private var targetPort: Int = 8080
    private lateinit var webView: WebView
    private lateinit var webViewClient: WebViewClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        targetPort = 8080 //start from 8080 again
        targetPort = findFreePort(targetPort)
        Log.i(taglog, "Start serving again at port $targetPort")
        server = InternalServer(this, targetPort)
        server.start()
        setContent {
            AndroidBootstrapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    AndroidView(factory = { context ->
                        WebView(context).apply {
                            settings.javaScriptEnabled = true
                            addJavascriptInterface(this@MainActivity, "Android")
                            webViewClient = WebViewClient()
                            loadUrl("http://localhost:$targetPort/index.html")
                            webView = this
                        }
                    })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(taglog,"stop serving")
        server.stop()
    }

    private fun findFreePort(startPort: Int): Int {
        var port = startPort
        while (port < 65535) {
            try {
                ServerSocket(port).use { it.localPort }
                Log.i(taglog, "Found free port: $port")
                return port
            } catch (e: IOException) {
                port++
            }
        }
        throw IOException("No free port found")
    }

    private fun executeJavaScript(script: String) {
        webView.post {
            webView.evaluateJavascript(script, null)
        }
    }

    private var webAppReady: Boolean = false
    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }
    @JavascriptInterface
    fun showLog(log: String) {
        Log.i(taglog, log)
    }

    @JavascriptInterface
    fun getDataFromAndroid(): String {
        return "This is data from Android"
    }

    @JavascriptInterface
    fun setReady(){
        Log.i(taglog, "setReady called")
        this.webAppReady = true
        executeJavaScript("start('ws://192.168.1.12:9001')")
    }

}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}