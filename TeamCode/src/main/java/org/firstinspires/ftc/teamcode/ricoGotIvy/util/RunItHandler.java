package org.firstinspires.ftc.teamcode.ricoGotIvy.util;

import android.app.Activity;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;


final class RunItHandler implements OpModeManagerNotifier.Notifications {

    private static final String logTag = "RunItHandler";

    HardwareMap hardwareMap;
    OpModeManagerImpl manager;

    private static String internalSearchPath;
    private EnumMap<CallTime, EnumMap<MethodFlavor, ArrayList<Method>>> indexedMethods;
    private Map<Class<?>, Object> classes = null;


    private void OpModeManagerDependencyInit() {
        Activity activity = AppUtil.getInstance().getActivity();
        manager = OpModeManagerImpl.getOpModeManagerOfActivity(activity);
        manager.registerListener(this);
        hardwareMap = manager.getHardwareMap();
    }

    RunItHandler(String searchPath) {
        internalSearchPath = searchPath;
        OpModeManagerDependencyInit();

        ArrayList<Method> rawMethods = AnnotatedMethodDexScanner.scanForMethods(
                hardwareMap,
                RunIt.class
        );
        indexedMethods = IndexMethodSet.indexMethodSet(rawMethods);
    }

    void setSearchPath(String searchPath) {
        internalSearchPath = searchPath;
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
    }

}

