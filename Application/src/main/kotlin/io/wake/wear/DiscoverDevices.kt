package io.wake.wear

import java.util.ArrayList
import java.net.InetAddress
import android.util.Log
import java.net.UnknownHostException
import java.io.IOException

public class DiscoverDevices(val subnet: String) {
    private val TAG = javaClass.getSimpleName()

    public fun scan(): ArrayList<String> {
        val hosts = ArrayList<String>()

        for (i in 0..255) {
            Log.d(TAG, "Trying: ${subnet}${i}")
            try {
                val inetAddress = InetAddress.getByName(subnet + i.toString());
                if (inetAddress.isReachable(1000)) {
                    hosts.add(inetAddress.getHostName())
                    Log.d(TAG, inetAddress.getHostName())
                }
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return hosts
    }
}