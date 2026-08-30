package org.firstinspires.ftc.teamcode.runItDev.util.runIt.internal;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.Call;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RI;

import java.lang.reflect.AccessibleObject;
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

    private static void invokeStaticNoParams(ArrayList<AccessibleObject> methods) {
        for (AccessibleObject AccessibleObject : methods) {
            try {
                Method method = IndexMethodSet.AccessibleObjectHelper.getMethod(AccessibleObject);
                method.invoke(null);
            } catch (Exception e) {
                RobotLog.ww(logTag, "invokeStaticNoParams" + AccessibleObject.toString(), e);
            }
        }
    }

    private static void invokeStaticHardwareMap(ArrayList<AccessibleObject> methods, HardwareMap hardwareMap) {
        for (AccessibleObject AccessibleObject : methods) {
            try {
                Method method = IndexMethodSet.AccessibleObjectHelper.getMethod(AccessibleObject);
                method.invoke(null, hardwareMap);
            } catch (Exception e) {
                RobotLog.ww(logTag, "invokeStaticHardwareMap" + AccessibleObject.toString(), e);
            }
        }
    }

    private static void invokeInstanceNoParams(ArrayList<AccessibleObject> methods) {
        for (AccessibleObject AccessibleObject : methods) {
            try {
                Method method = IndexMethodSet.AccessibleObjectHelper.getMethod(AccessibleObject);
                method.invoke(RI.g(method.getDeclaringClass()));
            } catch (InvocationTargetException | IllegalAccessException e) {
                RobotLog.ww(logTag, "invokeInstanceNoParams" + AccessibleObject.toString(), e);
            }
        }
    }

    private static void invokeInstanceHardwareMap(ArrayList<AccessibleObject> methods, HardwareMap hardwareMap) {
        for (AccessibleObject AccessibleObject : methods) {
            try {
                Method method = IndexMethodSet.AccessibleObjectHelper.getMethod(AccessibleObject);
                method.invoke(RI.g(method.getDeclaringClass()), hardwareMap);
            } catch (InvocationTargetException | IllegalAccessException e) {
                RobotLog.ww(logTag, "invokeInstanceHardwareMap" + AccessibleObject.toString(), e);
            }
        }
    }

    private static void internalInvoke(EnumMap<IndexMethodSet.MethodFlavor, ArrayList<AccessibleObject>> methods,
                                       HardwareMap hardwareMap)
    {
        try {
            invokeStaticNoParams(Objects.requireNonNull(methods.get(IndexMethodSet.MethodFlavor.STATIC_NO_PARAMS)));
            invokeStaticHardwareMap(Objects.requireNonNull(methods.get(IndexMethodSet.MethodFlavor.STATIC_HARDWAREMAP)), hardwareMap);
            invokeInstanceNoParams(Objects.requireNonNull(methods.get(IndexMethodSet.MethodFlavor.INSTANCE_NO_PARAMS)));
            invokeInstanceHardwareMap(Objects.requireNonNull(methods.get(IndexMethodSet.MethodFlavor.INSTANCE_HARDWAREMAP)), hardwareMap);
        } catch (NullPointerException e) {
            RobotLog.ww(logTag, "internalInvoke failed to get methods", e);
        }
    }


    static void invoke(Call call,
                       EnumMap<Call, EnumMap<IndexMethodSet.MethodFlavor, ArrayList<AccessibleObject>>> methods,
                       HardwareMap hardwareMap)
    {
        try {
            internalInvoke(Objects.requireNonNull(methods.get(call)), hardwareMap);
        } catch (NullPointerException e) {
            RobotLog.ww(logTag, "invoke failed to get methods", e);
        }
    }


}
