package mezzari.torres.lucas.realtimelocationapp.util

import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket

/**
 * @author Lucas T. Mezzari
 * @since 22/08/2019
 **/
object SocketUtils {
    lateinit var socket: Socket

    fun initialize(url: String) {
        if (!::socket.isInitialized) {
            socket = IO.socket(url)
        }
    }
}