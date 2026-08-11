package org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.internal;

import android.app.Activity;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

public class OpModeManagerHelper {
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
