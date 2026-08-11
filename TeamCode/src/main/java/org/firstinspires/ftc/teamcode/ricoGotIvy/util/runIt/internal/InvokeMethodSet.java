package org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.internal;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.RI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Objects;

final class InvokeMethodSet {

    private InvokeMethodSet() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static final String logTag = "Run It InvokeMethodSet";

    private static void invokeStaticNoParams(ArrayList<Method> methods) {
        for (Method method : methods) {
            try {
                method.invoke(null);
            } catch (Exception e) {
                RobotLog.ww(logTag, "invokeStaticNoParams" + method.getName(), e);
            }
        }
    }

    private static void invokeStaticHardwareMap(ArrayList<Method> methods, HardwareMap hardwareMap) {
        for (Method method : methods) {
            try {
                method.invoke(hardwareMap);
            } catch (Exception e) {
                RobotLog.ww(logTag, "invokeStaticHardwareMap" + method.getName(), e);
            }
        }
    }

    private static void invokeInstanceNoParams(ArrayList<Method> methods) {
        for (Method method : methods) {
            try {
                method.invoke(RI.g(method.getDeclaringClass()));
            } catch (InvocationTargetException | IllegalAccessException e) {
                RobotLog.ww(logTag, "invokeInstanceNoParams" + method.getName(), e);
            }
        }
    }

    private static void invokeInstanceHardwareMap(ArrayList<Method> methods, HardwareMap hardwareMap) {
        for (Method method : methods) {
            try {
                method.invoke(RI.g(method.getDeclaringClass()), hardwareMap);
            } catch (InvocationTargetException | IllegalAccessException e) {
                RobotLog.ww(logTag, "invokeInstanceHardwareMap" + method.getName(), e);
            }
        }
    }

    private static void internalInvoke(EnumMap<MethodFlavor, ArrayList<Method>> methods,
                                       HardwareMap hardwareMap)
    {
        try {
            invokeStaticNoParams(Objects.requireNonNull(methods.get(MethodFlavor.STATIC_NO_PARAMS)));
            invokeStaticHardwareMap(Objects.requireNonNull(methods.get(MethodFlavor.STATIC_HARDWAREMAP)), hardwareMap);
            invokeInstanceNoParams(Objects.requireNonNull(methods.get(MethodFlavor.INSTANCE_NO_PARAMS)));
            invokeInstanceHardwareMap(Objects.requireNonNull(methods.get(MethodFlavor.INSTANCE_HARDWAREMAP)), hardwareMap);
        } catch (NullPointerException e) {
            RobotLog.ww(logTag, "internalInvoke failed to get methods", e);
        }
    }


    static void invoke(CallTime callTime,
                       EnumMap<CallTime, EnumMap<MethodFlavor, ArrayList<Method>>> methods,
                       HardwareMap hardwareMap)
    {
        try {
            internalInvoke(Objects.requireNonNull(methods.get(callTime)), hardwareMap);
        } catch (NullPointerException e) {
            RobotLog.ww(logTag, "invoke failed to get methods", e);
        }
    }


}
