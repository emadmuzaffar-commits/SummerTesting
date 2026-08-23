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

    static Constructor<?> getConstructor(AccessibleObject AccessibleObject) {
        if (AccessibleObject instanceof Constructor) {
            try {
                return (Constructor<?>) AccessibleObject;
            } catch (Exception e) {
                RobotLog.ee(logTag, "Failed to cast: " + AccessibleObject.toString());
                throw e;
            }
        } else throw new IllegalArgumentException(
                "An AccessibleObject: " +
                        AccessibleObject.toString() +
                        "was not an instance of Constructor<?> and could not be cast"
        );
    }

    static Method getMethod(AccessibleObject AccessibleObject) {
        if (AccessibleObject instanceof Method) {
            try {
                return (Method) AccessibleObject;
            } catch (Exception e) {
                RobotLog.ee(logTag, "Failed to cast: " + AccessibleObject.toString());
                throw e;
            }
        } else throw new IllegalArgumentException(
                "An AccessibleObject: " +
                        AccessibleObject.toString() +
                        "was not an instance of Method and could not be cast"
        );
    }



}
