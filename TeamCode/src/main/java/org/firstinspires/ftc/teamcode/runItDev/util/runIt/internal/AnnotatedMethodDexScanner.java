package org.firstinspires.ftc.teamcode.runItDev.util.runIt.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

final class AnnotatedMethodDexScanner {
    static final String logTag = "Run It AnnotatedMethodDexScanner";
    
    private AnnotatedMethodDexScanner() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static ArrayList<Method> scanForMethods(HardwareMap hardwareMap,
                                                   Class<? extends Annotation> annotation)
    {
        return scanForMethods(hardwareMap, "org.firstinspires.ftc.teamcode", annotation);
    }

    public static ArrayList<Method> scanForMethods(
            HardwareMap hardwareMap,
            String packagePrefix,
            Class<? extends Annotation> annotation)
    {

        ArrayList<Method> results = new ArrayList<>();

        Context context = hardwareMap.appContext;
        ApplicationInfo appInfo = context.getApplicationInfo();
        ClassLoader classLoader = context.getClassLoader();

        List<String> apkPaths = new ArrayList<>();
        apkPaths.add(appInfo.sourceDir);

        if (appInfo.splitSourceDirs != null) {
            apkPaths.addAll(Arrays.asList(appInfo.splitSourceDirs));
        }

        for (String apkPath : apkPaths) {
            AnnotatedMethodDexScanner.scanDexFile(
                    apkPath,
                    packagePrefix,
                    annotation,
                    classLoader,
                    results
            );
        }
        
        return results;
    }

    @SuppressWarnings("deprecation") //Uses deprecated dexFile methods only for scan but not opening
    private static void scanDexFile(
            String apkPath,
            String packagePrefix,
            Class<? extends Annotation> annotation,
            ClassLoader classLoader,
            ArrayList<Method> results)
    {
        DexFile dexFile = null;
        try {
            dexFile = new DexFile(apkPath);
            Enumeration<String> entries = dexFile.entries();

            while (entries.hasMoreElements()) {
                String className = entries.nextElement();

                // This filter is extremely important for performance.
                if (!className.startsWith(packagePrefix)) {
                    continue;
                }

                inspectClass(className, annotation, classLoader, results);

            }
        } catch (IOException exception) {
            RobotLog.aa(logTag, "Error closing dex file", exception);
        } finally {
            if (dexFile != null) {
                try {
                    dexFile.close();
                } catch (IOException exception) {
                    RobotLog.aa(logTag, "Error closing dex file", exception);
                }
            }
        }
    }

    private static void inspectClass(
            String className,
            Class<? extends Annotation> annotation,
            ClassLoader classLoader,
            ArrayList<Method> results)
    {
        try {
            Class<?> clazz = classLoader.loadClass(className);
            if (clazz.isAnnotationPresent(annotation)) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(annotation)) {
                        results.add(method);
                    }
                }
                for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                    if (constructor.isAnnotationPresent(annotation)) {
                        //results.add(constructor);
                        // FIXME: 8/22/26
                    }
                }
            }
            for (Class<?> innerClass : clazz.getDeclaredClasses()) {
                inspectClass(innerClass.getName(), annotation, classLoader, results);
            }

        } catch (ClassNotFoundException | SecurityException | LinkageError exception) {
            RobotLog.aa(logTag, "Error scanning" + className, exception);
        }

    }

}
