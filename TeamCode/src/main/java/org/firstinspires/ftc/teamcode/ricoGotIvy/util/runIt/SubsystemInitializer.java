package org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.lang.reflect.Method;
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

    private static ArrayList<Object> initializeMethodParentClasses(HardwareMap hardwareMap, ArrayList<Method> methods) {
        ArrayList<Object> objects = new ArrayList<>();
        for (Method method : methods) {
            try {
                objects.add(method.invoke(null, hardwareMap));
            } catch (Exception exception) {
                RobotLog.ee(logTag, "Failed to initialize method parent subsystem: " +
                                method.getName() +
                                " !Will likely cause NPE!",
                        exception);
                throw new RuntimeException("Failed to initialize method parent subsystem: " +
                        method.getName(), exception);
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

    static Map<Class<?>, Object> initAndCreateMap(HardwareMap hardwareMap, ArrayList<Method> methods) {
        return createMap(initializeMethodParentClasses(hardwareMap, methods));
    }


}
