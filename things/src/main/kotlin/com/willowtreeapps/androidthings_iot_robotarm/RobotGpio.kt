package com.willowtreeapps.androidthings_iot_robotarm

import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import com.willowtreeapps.commonlib.*
import java.io.IOException

class RobotGpio {

    private val TAG = RobotGpio::class.java.simpleName

    private val GPIO_PIN_BCM04 = "BCM4"
    private val GPIO_PIN_BCM05 = "BCM5"
    private val GPIO_PIN_BCM06 = "BCM6"
    private val GPIO_PIN_BCM12 = "BCM12"
    private val GPIO_PIN_BCM17 = "BCM17"
    private val GPIO_PIN_BCM22 = "BCM22"
    private val GPIO_PIN_BCM23 = "BCM23"
    private val GPIO_PIN_BCM24 = "BCM24"
    private val GPIO_PIN_BCM25 = "BCM25"
    private val GPIO_PIN_BCM27 = "BCM27"

    private lateinit var gpioMap: Map<String, Gpio?>

    fun initGpio() {
        val service = PeripheralManagerService()
        Log.d(TAG, "Available GPIO: " + service.gpioList)

        try {
            gpioMap = mapOf(
                    GPIO_PIN_BCM04 to service.openGpio(GPIO_PIN_BCM04),
                    GPIO_PIN_BCM05 to service.openGpio(GPIO_PIN_BCM05),
                    GPIO_PIN_BCM06 to service.openGpio(GPIO_PIN_BCM06),
                    GPIO_PIN_BCM12 to service.openGpio(GPIO_PIN_BCM12),
                    GPIO_PIN_BCM17 to service.openGpio(GPIO_PIN_BCM17),
                    GPIO_PIN_BCM22 to service.openGpio(GPIO_PIN_BCM22),
                    GPIO_PIN_BCM23 to service.openGpio(GPIO_PIN_BCM23),
                    GPIO_PIN_BCM24 to service.openGpio(GPIO_PIN_BCM24),
                    GPIO_PIN_BCM25 to service.openGpio(GPIO_PIN_BCM25),
                    GPIO_PIN_BCM27 to service.openGpio(GPIO_PIN_BCM27)
            ).onEach {
                it.value?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error on PeripheralIO API", e)
        }
    }

    fun closeAll() {
        // Close the Gpio pins
        Log.i(TAG, "Closing LED GPIO pin")
        try {
            gpioMap.forEach({ it.value?.close() })
        } catch (e: IOException) {
            Log.e(TAG, "Error on PeripheralIO API", e)
        }
    }

    fun toggleGpio(robotArmState: RobotArmState) {
        robotArmState.armState.forEach({
            when (it.key) {
                KEY_ROTATE -> {
                    helperToggle(it.value, Pair(gpioMap[GPIO_PIN_BCM04], gpioMap[GPIO_PIN_BCM05]))
                }
                KEY_ELEVATE -> {
                    helperToggle(it.value, Pair(gpioMap[GPIO_PIN_BCM06], gpioMap[GPIO_PIN_BCM12]))
                }
                KEY_ARTICULATE -> {
                    helperToggle(it.value, Pair(gpioMap[GPIO_PIN_BCM17], gpioMap[GPIO_PIN_BCM22]))
                }
                KEY_SCOPE -> {
                    helperToggle(it.value, Pair(gpioMap[GPIO_PIN_BCM23], gpioMap[GPIO_PIN_BCM24]))
                }
                KEY_CLAW -> {
                    helperToggle(it.value, Pair(gpioMap[GPIO_PIN_BCM25], gpioMap[GPIO_PIN_BCM27]))
                }
            }
        })
    }

    private fun helperToggle(state: String, pins: Pair<Gpio?, Gpio?>) {
        when (state) {
            STATE_FORWARD -> {
                pins.second?.value = false
                pins.first?.value = true
            }
            STATE_REVERSE -> {
                pins.first?.value = false
                pins.second?.value = true
            }
            else -> {
                pins.first?.value = false
                pins.second?.value = false
            }
        }
    }
}
