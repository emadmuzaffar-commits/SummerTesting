package org.firstinspires.ftc.teamcode.runItDev.util.runIt.internal;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.Call;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RunIt;

import java.lang.reflect.Constructor;
import java.lang.reflect.AccessibleObject;
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


    private static EnumMap<Call, ArrayList<AccessibleObject>> indexMethodCallTime(ArrayList<AccessibleObject> methods) {
        EnumMap<Call, ArrayList<AccessibleObject>> methodListMap = new EnumMap<>(Call.class);
        for (Call call : Call.values()) {
            methodListMap.put(call, new ArrayList<>());
        }

        for (AccessibleObject method : methods) {
            try {
                if (method.isAnnotationPresent(RunIt.class)) {
                    Call calltime = Objects.requireNonNull(method.getAnnotation(RunIt.class)).callTime();
                    Objects.requireNonNull(methodListMap.get(calltime)).add(method);
                }
            } catch (Exception exception) {
                RobotLog.ww(logTag, "Failed to index method call time: " +
                        method.toString() +
                        "Method will not be run",
                        exception);
            }
        }

        return methodListMap;
    }

    private static EnumMap<MethodFlavor, ArrayList<AccessibleObject>> indexMethodFlavor(ArrayList<AccessibleObject> methods) {
        EnumMap<MethodFlavor, ArrayList<AccessibleObject>> methodListMap = new EnumMap<>(MethodFlavor.class);
        for (MethodFlavor methodFlavor : MethodFlavor.values()) {
            methodListMap.put(methodFlavor, new ArrayList<>());
        }

        for (AccessibleObject AccessibleObject : methods) {

            try {
                if (Objects.requireNonNull(AccessibleObject.getAnnotation(RunIt.class)).callTime() == Call.SUBSYSTEM) {
                    Constructor<?> constructor = AccessibleObjectHelper.getConstructor(AccessibleObject);
                    if (Modifier.isPublic(constructor.getModifiers())) {
                        Objects.requireNonNull(methodListMap.get(MethodFlavor.SUBSYSTEM_FLAVOR)).add(AccessibleObject);
                    }
                    continue;
                }
                Method method = AccessibleObjectHelper.getMethod(AccessibleObject);
                if (Modifier.isPublic(method.getModifiers())) {
                    if (Modifier.isStatic(method.getModifiers())) {
                        if (method.getParameterCount() == 0) {
                            Objects.requireNonNull(methodListMap.get(MethodFlavor.STATIC_NO_PARAMS)).add(AccessibleObject);
                        } else {
                            Objects.requireNonNull(methodListMap.get(MethodFlavor.STATIC_HARDWAREMAP)).add(AccessibleObject);
                        }
                    } else if (!Modifier.isStatic(method.getModifiers())) {
                        if (method.getParameterCount() == 0) {
                            Objects.requireNonNull(methodListMap.get(MethodFlavor.INSTANCE_NO_PARAMS)).add(AccessibleObject);
                        } else if (method.getParameterCount() == 1) {
                                Objects.requireNonNull(methodListMap.get(MethodFlavor.INSTANCE_HARDWAREMAP)).add(AccessibleObject);
                        }
                    }
                }
            } catch (Exception exception) {
                RobotLog.ww(logTag, "Failed to index method flavor: " +
                        AccessibleObject.toString() +
                        "Method will not be run",
                        exception);
            }
        }

        return methodListMap;
    }

    static EnumMap<Call, EnumMap<MethodFlavor, ArrayList<AccessibleObject>>> indexMethodSet(ArrayList<AccessibleObject> methods) {
        EnumMap<Call, EnumMap<MethodFlavor, ArrayList<AccessibleObject>>> methodListMap = new EnumMap<>(Call.class);
        EnumMap<Call, ArrayList<AccessibleObject>> callTimeArrayListEnumMap = indexMethodCallTime(methods);

        for (Call call : Call.values()) {
            methodListMap.put(call, indexMethodFlavor(Objects.requireNonNull(callTimeArrayListEnumMap.get(call))));
            if (call == Call.SUBSYSTEM) continue;
            Objects.requireNonNull(Objects.requireNonNull(methodListMap.get(call)).get(MethodFlavor.SUBSYSTEM_FLAVOR)).clear();
        }

        return methodListMap;
    }

    enum MethodFlavor {
        INSTANCE_HARDWAREMAP,
        INSTANCE_NO_PARAMS,
        STATIC_HARDWAREMAP,
        STATIC_NO_PARAMS,
        SUBSYSTEM_FLAVOR
    }

    static final class AccessibleObjectHelper {

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
}
