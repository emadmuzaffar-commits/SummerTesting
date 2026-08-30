package org.firstinspires.ftc.teamcode.runItDev.util.runIt.internal;

import android.app.Activity;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.Call;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RunIt;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;


public final class RunItHandler implements OpModeManagerNotifier.Notifications {

    private static final String logTag = "RunItHandler";

    private HardwareMap hardwareMap;
    private final OpModeManagerImpl manager;
    private final EnumMap<Call, EnumMap<IndexMethodSet.MethodFlavor, ArrayList<AccessibleObject>>> indexedMap;
    private Map<Class<?>, Object> classes = null;
    private static boolean instantiated = false;
    private static ArrayList<Class<? extends OpMode>> opModes = new ArrayList<>();

    public void registerOpMode(Class<? extends OpMode> opMode) {
        opModes.add(opMode);
    }
    public RunItHandler(String searchPath, Class<? extends OpMode> opMode) {
        if (instantiated) {
            throw new RuntimeException("RunItHandler has already been instantiated");
        }
        instantiated = true;
        registerOpMode(opMode);
        manager = OpModeManagerHelper.getManagerAndRegister(this);
        hardwareMap = manager.getHardwareMap();

        ArrayList<AccessibleObject> rawSet = AnnotatedMethodDexScanner.scanForMethods(
                hardwareMap,
                searchPath,
                RunIt.class
        );
        indexedMap = IndexMethodSet.indexMethodSet(rawSet);
    }

    ///Get subsystem
    public Object g(Class<?> subsystem) {
        try {
            Object instance = Objects.requireNonNull(classes.get(subsystem));
            Assert.assertTrue(instance.getClass() == subsystem);
            return instance;
        } catch (NullPointerException e) {
            RobotLog.ee(logTag, "Subsystem not found in reference set", e);
            return null;
        }
    }
    
    private static boolean isNotRegisteredOpMode(OpMode opMode) {
        return !opModes.contains(opMode.getClass());
    }

    @Override
    public void onOpModePreInit(OpMode opMode) {
        hardwareMap = opMode.hardwareMap;

        if (isNotRegisteredOpMode(opMode)) return;
        classes = SubsystemInitializer.initAndCreateMap(
                hardwareMap,
                Objects.requireNonNull(indexedMap.get(Call.SUBSYSTEM)).get(IndexMethodSet.MethodFlavor.SUBSYSTEM_FLAVOR)
        );
        InvokeMethodSet.invoke(Call.INIT, indexedMap, hardwareMap);
    }

    @Override
    public void onOpModePreStart(OpMode opMode) {
        hardwareMap = opMode.hardwareMap;

        if (isNotRegisteredOpMode(opMode)) return;
        InvokeMethodSet.invoke(Call.START, indexedMap, hardwareMap);
    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        hardwareMap = opMode.hardwareMap;

        if (isNotRegisteredOpMode(opMode)) return;
        InvokeMethodSet.invoke(Call.STOP, indexedMap, hardwareMap);
    }

    public static final class OpModeManagerHelper {

        private OpModeManagerHelper() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
        public static OpModeManagerImpl getManagerAndRegister(OpModeManagerNotifier.Notifications listener) {
            OpModeManagerImpl manager;
            Activity activity = AppUtil.getInstance().getActivity();
            manager = OpModeManagerImpl.getOpModeManagerOfActivity(activity);
            manager.registerListener(listener);
            return manager;
        }

        public static OpModeManagerImpl getManager() {
            OpModeManagerImpl manager;
            Activity activity = AppUtil.getInstance().getActivity();
            manager = OpModeManagerImpl.getOpModeManagerOfActivity(activity);
            return manager;
        }
    }
}

