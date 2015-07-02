package io.wake.wear

import java.util.regex.Pattern
import java.net.InetAddress
import java.net.DatagramPacket
import java.net.DatagramSocket

public class MagicPacket(val mac: String, val ip: String) {
    private val TAG = "MagicPacket"

    private val SEPARATOR = ':'
    private val PORT = 9

    companion object {
        public val BROADCAST: String = "255.255.255.255"
    }

    public fun send(): Unit = send(PORT)

    public fun send(port: Int) {
        val magicBytes = createMagicPacket()

        // create socket to IP
        val address = InetAddress.getByName(ip)
        val packet = DatagramPacket(magicBytes, magicBytes.size(), address, port)
        val socket = DatagramSocket()
        socket.send(packet)
        socket.close()
    }

    fun createMagicPacket(): ByteArray {
        val hex = validateMac(mac)

        val macBytes = hex.map { Integer.parseInt(it, 16).toByte() }
        val bytes = ByteArray(102)
        ((1..6).map { 0xFF.toByte() } + (1..16).flatMap { macBytes }).forEachIndexed { (i, byte) ->
            bytes[i] = byte
        }
        return bytes
    }

    private fun validateMac(mac: String): Array<String> {
        var newMac = with (mac.replace(";", ":")) {
            if (matches("([a-zA-Z0-9]){12}")) {
                mapIndexed {(i, c) -> if ((i > 1) && (i % 2 == 0)) c + ":" else c }
            } else {
                this
            }
        } as String

        // regexp pattern match a valid MAC address
        val pattern = Pattern.compile("((([0-9a-fA-F]){2}[-:]){5}([0-9a-fA-F]){2})")
        val matcher = pattern.matcher(newMac)

        if (matcher.find()) {
            return matcher.group().split("(\\:|\\-)")
        } else {
            throw IllegalArgumentException("Invalid MAC address")
        }
    }
}