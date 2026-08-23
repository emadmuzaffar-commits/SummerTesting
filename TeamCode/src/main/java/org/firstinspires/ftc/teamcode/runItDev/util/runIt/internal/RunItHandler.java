package org.firstinspires.ftc.teamcode.runItDev.util.runIt.internal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

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

    private final HardwareMap hardwareMap;
    private final OpModeManagerImpl manager;
    private final EnumMap<Call, EnumMap<MethodFlavor, ArrayList<AccessibleObject>>> indexedMap;
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
            RobotLog.ee(logTag, "Subsystem not found in reference set");
            return null;
        }
    }
    
    private static boolean isNotRegisteredOpMode(OpMode opMode) {
        return !opModes.contains(opMode.getClass());
    }

    @Override
    public void onOpModePreInit(OpMode opMode) {
        if (isNotRegisteredOpMode(opMode)) return;
        classes = SubsystemInitializer.initAndCreateMap(
                hardwareMap,
                Objects.requireNonNull(indexedMap.get(Call.SUBSYSTEM)).get(MethodFlavor.SUBSYSTEM_FLAVOR)
        );
        InvokeMethodSet.invoke(Call.INIT, indexedMap, hardwareMap);
    }

    @Override
    public void onOpModePreStart(OpMode opMode) {
        if (isNotRegisteredOpMode(opMode)) return;
        InvokeMethodSet.invoke(Call.START, indexedMap, hardwareMap);
    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        if (isNotRegisteredOpMode(opMode)) return;
        InvokeMethodSet.invoke(Call.STOP, indexedMap, hardwareMap);
    }

}

