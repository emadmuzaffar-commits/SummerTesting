package org.firstinspires.ftc.teamcode.ricoGotIvy.util

object RI {
    private var runIt: RunItHandler? = null;

    @JvmStatic
    fun go(searchPaths: String = "org.firstinspires.ftc.teamcode") {
        runIt = RunItHandler(searchPaths)
    }

    @JvmStatic
    fun g(subsystem: Class<*>): Any {
        return runIt!!.g(subsystem)
    }
}