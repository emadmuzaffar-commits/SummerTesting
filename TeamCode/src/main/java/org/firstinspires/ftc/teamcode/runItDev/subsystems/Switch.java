package org.firstinspires.ftc.teamcode.runItDev.subsystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.Call;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RunIt;

public class Switch {

    private final AnalogSensor aSwitch;

    @RunIt(callTime = Call.SUBSYSTEM)
    public Switch(HardwareMap hardwareMap) {
        aSwitch = hardwareMap.get(AnalogSensor.class, "switch");
    }

    public boolean isPressed() {
        return aSwitch.readRawVoltage() > 2.5;
    }

}
