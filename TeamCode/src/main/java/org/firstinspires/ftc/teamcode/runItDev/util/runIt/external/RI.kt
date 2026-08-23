package org.firstinspires.ftc.teamcode.runItDev.util.runIt.external

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.internal.RunItHandler

object RI {
    private var runIt: RunItHandler? = null

    @JvmStatic
    fun go(searchPaths: String = "org.firstinspires.ftc.teamcode", opMode: Class<out OpMode>) {
        if (runIt == null) {
            runIt = RunItHandler(searchPaths, opMode)
            return
        } else {
            runIt!!.registerOpMode(opMode)
        }
    }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T : Any> g(subsystem: Class<T>): T {
        try {
            return runIt!!.g(subsystem) as T
        } catch (nullPointerException: NullPointerException) {
            throw RuntimeException(
                "Make sure RI.go was called, RI failed to search reference set",
                nullPointerException
            )
        }

    }
}