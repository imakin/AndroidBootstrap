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
 * @purpose: the web server for androi
 * @author: Muhammad Ramdan Izzulmakin
 * @date: 2024-August
 */
import fi.iki.elonen.NanoHTTPD
import android.content.Context
import android.util.Log
import java.io.IOException

class InternalServer(private val context: Context, port: Int) : NanoHTTPD(port) {
    private final val taglog = "MMM========MMM"

    @Throws(IOException::class)
    override fun serve(session: IHTTPSession): Response {
        val uri = session.uri
        return serveFile(uri)
    }

    private fun serveFile(uri: String): Response {
        Log.i(taglog, "Requested file: $uri")
        val fileName = "web/${uri.substringAfterLast("/")}"
        val mimeType = getMimeType(fileName)
        val inputStream = context.assets.open(fileName)
        Log.i(taglog, "Serving $fileName")
        return newChunkedResponse(Response.Status.OK, mimeType, inputStream)
    }


    private fun getMimeType(fileName: String): String {
        val v:String = when {
            fileName.endsWith(".html") -> "text/html"
            fileName.endsWith(".js") -> "application/javascript"
            fileName.endsWith(".css") -> "text/css"
            fileName.endsWith(".png") -> "image/png"
            fileName.endsWith(".jpg") -> "image/jpeg"
            fileName.endsWith(".jpeg") -> "image/jpeg"
            fileName.endsWith(".webp") -> "image/webp"
            fileName.endsWith(".gif") -> "image/gif"
            fileName.endsWith(".mp4") -> "video/mp4"
            // Add more file types as needed
            else -> "application/octet-stream"
        }
        Log.i(taglog, "MimeType is: $v")
        return v
    }
}
