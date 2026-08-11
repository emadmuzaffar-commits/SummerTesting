package org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.internal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.system.Assert;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.CallTime;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.RunIt;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;


public final class RunItHandler implements OpModeManagerNotifier.Notifications {

    private static final String logTag = "RunItHandler";

    private final HardwareMap hardwareMap;
    private final OpModeManagerImpl manager;
    private final EnumMap<CallTime, EnumMap<MethodFlavor, ArrayList<Method>>> indexedMethods;
    private Map<Class<?>, Object> classes = null;
    private static boolean instantiated = false;



    public RunItHandler(String searchPath) {
        if (instantiated) {
            throw new RuntimeException("RunItHandler has already been instantiated");
        }
        instantiated = true;
        manager = OpModeManagerHelper.getManagerAndRegister(this);
        hardwareMap = manager.getHardwareMap();

        ArrayList<Method> rawMethods = AnnotatedMethodDexScanner.scanForMethods(
                hardwareMap,
                searchPath,
                RunIt.class
        );
        indexedMethods = IndexMethodSet.indexMethodSet(rawMethods);
    }

    ///Get subsystem
    Object g(Class<?> subsystem) {
        try {
            Object instance = Objects.requireNonNull(classes.get(subsystem));
            Assert.assertTrue(instance.getClass() == subsystem);
            return instance;
        } catch (NullPointerException e) {
            RobotLog.ee(logTag, "Subsystem not found in reference set");
            return null;
        }
    }
    

    @Override
    public void onOpModePreInit(OpMode opMode) {
        classes = SubsystemInitializer.initAndCreateMap(
                hardwareMap,
                Objects.requireNonNull(indexedMethods.get(CallTime.INIT)).get(MethodFlavor.SUBSYSTEM)
        );
        InvokeMethodSet.invoke(CallTime.INIT, indexedMethods, hardwareMap);
    }

    @Override
    public void onOpModePreStart(OpMode opMode) {
        InvokeMethodSet.invoke(CallTime.START, indexedMethods, hardwareMap);
    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        InvokeMethodSet.invoke(CallTime.STOP, indexedMethods, hardwareMap);
        manager.unregisterListener(this);
        instantiated = false;
    }

}

