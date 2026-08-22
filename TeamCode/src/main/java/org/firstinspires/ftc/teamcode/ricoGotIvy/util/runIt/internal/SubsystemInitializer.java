package org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.internal;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.util.ArrayList;
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

    private static ArrayList<Object> initializeMethodParentClasses(HardwareMap hardwareMap, ArrayList<Executable> methods) {
        ArrayList<Object> objects = new ArrayList<>();
        for (Executable method : methods) {
            try {
                Constructor<?> constructor = ExecutableHelper.getConstructor(method);
                objects.add(constructor.newInstance(hardwareMap));
            } catch (Exception exception) {
                RobotLog.ee(logTag, "Failed to initialize method parent subsystem: " +
                                method.toString() +
                                " !Will likely cause NPE!",
                        exception);
                throw new RuntimeException("Failed to initialize method parent subsystem: " +
                        method.toString(), exception);
            }
        }
        try {
            objects.add(Constants.createFollower(hardwareMap));
        } catch (Exception exception) {
            RobotLog.ee(logTag, "Failed to initialize PedroPathing follower" +
                            " !Will likely cause NPE!",
                    exception);
            throw new RuntimeException("Failed to initialize PedroPathing follower", exception);
        }
        return objects;
    }

    static Map<Class<?>, Object> initAndCreateMap(HardwareMap hardwareMap, ArrayList<Executable> methods) {
        return createMap(initializeMethodParentClasses(hardwareMap, methods));
    }


}
