package org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt

object RI {
    private var runIt: RunItHandler? = null

    @JvmStatic
    fun go(searchPaths: String = "org.firstinspires.ftc.teamcode") {
        runIt = RunItHandler(searchPaths)
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