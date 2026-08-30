package org.firstinspires.ftc.teamcode.runItDev.util.runIt.internal;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

final class SubsystemInitializer {

    private static final String logTag = "SubsystemInitializer";

    private SubsystemInitializer() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static Map<Class<?>, Object> createMap(ArrayList<Object> subsystems) {
        Map<Class<?>, Object> references = new HashMap<>();
        for (Object subsystem : subsystems) {
            references.put(subsystem.getClass(), subsystem);
        }
        return references;
    }

    private static ArrayList<Object> initializeSubsystems(HardwareMap hardwareMap, ArrayList<AccessibleObject> constructors) {
        ArrayList<Object> objects = new ArrayList<>();

        for (AccessibleObject accessibleObject : constructors) {
            Constructor<?> constructor = AccessibleObjectHelper.getConstructor(accessibleObject);
            try {
                objects.add(constructor.newInstance(hardwareMap));
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                Throwable cause = e.getCause() != null ? e.getCause() : e;
                RobotLog.ee(logTag, "Failed to initialize subsystem: " +
                                accessibleObject.toString() +
                                cause +
                                Arrays.toString(e.getStackTrace()),
                        e);
            }
        }

        try {
            objects.add(Constants.createFollower(hardwareMap));
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            RobotLog.ee(logTag, "Failed to initialize follower: " +
                            cause +
                            Arrays.toString(e.getStackTrace()),
                    e);
            throw e;
        }

        return objects;

    }

    static Map<Class<?>, Object> initAndCreateMap(HardwareMap hardwareMap, ArrayList<AccessibleObject> methods) {
        return createMap(initializeSubsystems(hardwareMap, methods));
    }


}
