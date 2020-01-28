package com.example.neube.smartdrive

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManager
import com.neuberfran.androidthings.driver.SmartDrive.SmartDrive
import java.io.IOException
import java.util.logging.Logger

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class MainActivity : AppCompatActivity() {

    private val LOG = Logger.getLogger(this.javaClass.name)
    private val TAG = MainActivity::class.java.simpleName

    var fcmotorUmAvalue0 = hashMapOf("fcmotoruma" to 0)

    var fcmotorUmAvalue1 = hashMapOf("fcmotoruma" to 1)

    var mSmartDrive: SmartDrive? = null

    internal var I2C_PIN_NAME = "I2C1"
    internal val I2C_ADDRESS_SMARTDRIVE = 0x1B
    internal var SmartDrive_COMMAND = 0x41

    internal var CMD_R = 0x52
    internal var CMD_S = 0x53

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSmartDrive = SmartDrive(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)

        mSmartDrive?.command(CMD_R)

        val manager = PeripheralManager.getInstance()

        JanelaUmEsquerda.addSnapshotListener { snapshot, e ->

            if (e != null) {
                Log.w(LOG.toString(), "Listen failed.", e)
                return@addSnapshotListener
            }

            var FCMotorUmA: Gpio? = null

            try {

                FCMotorUmA = manager.openGpio(BoardDefaults.getGPIOForButton21())

                Log.i(ContentValues.TAG, "99 99 99 ")

                FCMotorUmA.setDirection(Gpio.DIRECTION_IN)
                // Step 3. Enable edge trigger events.
                FCMotorUmA.setEdgeTriggerType(Gpio.EDGE_FALLING)

                FCMotorUmA.registerGpioCallback(mCallUmEsquerda)


            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "Error on PeripheralIO API", e)
            }

        }

        db.collection("smartmodel").document("motores").set(fcmotorUmAvalue0, SetOptions.merge())
    }

    var mCallUmEsquerda = object : GpioCallback {

        override fun onGpioEdge(buttonUmA: Gpio): Boolean {

            Log.i(ContentValues.TAG, "GPIO changed, button 94101 94101 94101" + buttonUmA.value)

            var pararRefUmEsquerda = db.collection("smartmodel").document("motores")

            var taskUmEsquerda: Task<DocumentSnapshot> = pararRefUmEsquerda.get()

            var snapUmEsquerda: DocumentSnapshot = Tasks.await(taskUmEsquerda)

            var xPararUmEsquerda = snapUmEsquerda.toObject(PararUm::class.java)?.PararUmEsquerda

            if (buttonUmA.value) {

                db.collection("smartmodel").document("motores").set(fcmotorUmAvalue1, SetOptions.merge())

                Log.i(ContentValues.TAG, "passei 3 passei 3 passei 3")

            } else if (!buttonUmA.value && xPararUmEsquerda!!.equals(0)) {

                db.collection("smartmodel").document("motores").set(fcmotorUmAvalue0, SetOptions.merge())

                Log.i(ContentValues.TAG, "passei 911 passei 911 passei 911" + buttonUmA.value)

                var pararRefUmEsquerda = db.collection("smartmodel").document("motores")

                var pauloUmEsquerda = true

            }

            return true
        }

        override fun onGpioError(gpio: Gpio?, error: Int) = LOG.severe("$gpio Error event $error")
    }


//    override fun onDestroy() {
//        super.onDestroy()
//
//        try {
//            FCMotorUmA?.close()
//            FCMotorUmA = null
//        } catch (e: IOException) {
//            Log.w(TAG, "Unable to close GPIO", e)
//        }
//    }
}