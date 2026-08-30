package org.firstinspires.ftc.teamcode.runItDev.util.runIt.internal;

import com.qualcomm.robotcore.util.RobotLog;

import java.lang.reflect.Constructor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

final class AccessibleObjectHelper {

    static final String logTag = "AccessibleObjectHelper";

    private AccessibleObjectHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static Constructor<?> getConstructor(AccessibleObject accessibleObject) {
        if (accessibleObject instanceof Constructor) {
            try {
                return (Constructor<?>) accessibleObject;
            } catch (ClassCastException e) {
                RobotLog.ee(logTag, "Failed to cast: " + accessibleObject.toString());
                throw e;
            }
        } else throw new IllegalArgumentException(
                "An AccessibleObject: " +
                        accessibleObject.toString() +
                        "was not an instance of Constructor<?> and could not be cast"
        );
    }

    static Method getMethod(AccessibleObject accessibleObject) {
        if (accessibleObject instanceof Method) {
            try {
                return (Method) accessibleObject;
            } catch (ClassCastException e) {
                RobotLog.ee(logTag, "Failed to cast: " + accessibleObject.toString());
                throw e;
            }
        } else throw new IllegalArgumentException(
                "An AccessibleObject: " +
                        accessibleObject.toString() +
                        "was not an instance of Method and could not be cast"
        );
    }



}
