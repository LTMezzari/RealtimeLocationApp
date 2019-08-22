package mezzari.torres.lucas.realtimelocationapp.flow.map

import com.github.nkzawa.socketio.client.Socket
import mezzari.torres.lucas.realtimelocationapp.util.SocketUtils

/**
 * @author Lucas T. Mezzari
 * @since 22/08/2019
 **/
class MapsActivityViewModel {

    private val socket: Socket get() = SocketUtils.socket

    var username: String = ""
    var ipAddress: String? = null
        set(value) {
            field = value
            this.url = "http://$ipAddress/"
        }
    var url: String? = null
        set(value) {
            field = value
            connectSocket()
        }

    fun connectSocket() {
        url?.run {
            SocketUtils.initialize(this)
            socket.run {
                connect()
            }
        }
    }

    fun disconnectSocket() {
        url ?: return
        socket.run {
            disconnect()
        }
    }
}