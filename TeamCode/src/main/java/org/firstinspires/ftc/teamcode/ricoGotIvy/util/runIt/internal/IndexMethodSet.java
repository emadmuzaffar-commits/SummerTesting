package org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.internal;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.CallTime;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.RunIt;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Objects;

final class IndexMethodSet {

    private IndexMethodSet() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static final String logTag = "Run It IndexMethodSet";



    private static EnumMap<CallTime, ArrayList<Method>> indexMethodCallTime(ArrayList<Method> methods) {
        EnumMap<CallTime, ArrayList<Method>> methodListMap = new EnumMap<>(CallTime.class);
        for (CallTime callTime : CallTime.values()) {
            methodListMap.put(callTime, new ArrayList<>());
        }

        for (Method method : methods) {
            try {
                if (method.isAnnotationPresent(RunIt.class)) {
                    CallTime calltime = Objects.requireNonNull(method.getAnnotation(RunIt.class)).callTime();
                    Objects.requireNonNull(methodListMap.get(calltime)).add(method);
                }
            } catch (Exception exception) {
                RobotLog.ee(logTag, "Failed to index method flavor: " + method.getName(), exception);
            }
        }

        return methodListMap;
    }

    private static EnumMap<MethodFlavor, ArrayList<Method>> indexMethodFlavor(ArrayList<Method> methods) {
        EnumMap<MethodFlavor, ArrayList<Method>> methodListMap = new EnumMap<>(MethodFlavor.class);
        for (MethodFlavor methodFlavor : MethodFlavor.values()) {
            methodListMap.put(methodFlavor, new ArrayList<>());
        }

        for (Method method : methods) {
            try {
                if (method.isAccessible() && Modifier.isPublic(method.getModifiers())) {
                     if (Objects.requireNonNull(method.getAnnotation(RunIt.class)).callTime() == CallTime.SUBSYSTEM) {
                        Objects.requireNonNull(methodListMap.get(MethodFlavor.SUBSYSTEM)).add(method);
                    }
                    if (Modifier.isStatic(method.getModifiers())) {
                        if (method.getParameterCount() == 0) {
                            Objects.requireNonNull(methodListMap.get(MethodFlavor.STATIC_NO_PARAMS)).add(method);
                        } else {
                            Objects.requireNonNull(methodListMap.get(MethodFlavor.STATIC_HARDWAREMAP)).add(method);
                        }
                    } else if (!Modifier.isStatic(method.getModifiers())) {
                        if (method.getParameterCount() == 0) {
                            Objects.requireNonNull(methodListMap.get(MethodFlavor.INSTANCE_NO_PARAMS)).add(method);
                        } else if (method.getParameterCount() == 1) {
                                Objects.requireNonNull(methodListMap.get(MethodFlavor.INSTANCE_HARDWAREMAP)).add(method);
                        }
                    }
                }
            } catch (Exception exception) {
                RobotLog.ee(logTag, "Failed to index method flavor: " + method.getName(), exception);
            }
        }

        return methodListMap;
    }

    static EnumMap<CallTime, EnumMap<MethodFlavor, ArrayList<Method>>> indexMethodSet(ArrayList<Method> methods) {
        EnumMap<CallTime, EnumMap<MethodFlavor, ArrayList<Method>>> methodListMap = new EnumMap<>(CallTime.class);
        EnumMap<CallTime, ArrayList<Method>> callTimeArrayListEnumMap = indexMethodCallTime(methods);

        for (CallTime callTime : CallTime.values()) {
            methodListMap.put(callTime, indexMethodFlavor(Objects.requireNonNull(callTimeArrayListEnumMap.get(callTime))));
            if (callTime == CallTime.INIT) continue;
            Objects.requireNonNull(Objects.requireNonNull(methodListMap.get(callTime)).get(MethodFlavor.SUBSYSTEM)).clear();
        }

        return methodListMap;
    }
}
