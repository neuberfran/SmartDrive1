package com.example.neube.smartdrive

import android.content.res.Resources
import android.os.Build
import android.content.Context

object BoardDefaults {
    private val DEVICE_RPI3 = "rpi3"
    private val DEVICE_IMX7D_PICO = "imx7d_pico"
    private var sBoardVariant = ""

    class BoardDefaults(public val context: Context) {

        public val res = context.resources

    }

    fun getGPIOForLED(): String {
        when (boardVariant) {
            DEVICE_RPI3 -> return "BCM18"
            DEVICE_IMX7D_PICO -> return "GPIO2_IO03"
            else -> throw IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE)
        }
    }


    fun getGPIOForButton15(): String {
        when (boardVariant) {
            DEVICE_RPI3 -> return "BCM15"
            DEVICE_IMX7D_PICO -> return "GPIO2_IO03"
            else -> throw IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE)
        }
    }

    fun getGPIOForButton17(): String {
        when (boardVariant) {
            DEVICE_RPI3 -> return "BCM17"
            DEVICE_IMX7D_PICO -> return "GPIO2_IO03"
            else -> throw IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE)
        }
    }

    fun getGPIOForButton18(): String {
        when (boardVariant) {
            DEVICE_RPI3 -> return "BCM18"
            DEVICE_IMX7D_PICO -> return "GPIO2_IO03"
            else -> throw IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE)
        }
    }

    fun getGPIOForButton21(): String {
        when (boardVariant) {
            DEVICE_RPI3 -> return "BCM21"
            DEVICE_IMX7D_PICO -> return "GPIO2_IO03"
            else -> throw IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE)
        }
    }

    private

    val boardVariant: String
        get() {
            if (!sBoardVariant.isEmpty()) {
                return sBoardVariant
            }
            sBoardVariant = Build.DEVICE

            return sBoardVariant
        }
}