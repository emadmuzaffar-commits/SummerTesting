package org.firstinspires.ftc.teamcode.runItDev.util.runIt.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

final class ExecutableHelper {
    private ExecutableHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static Constructor<?> getConstructor(Executable executable) {
        if (executable instanceof Constructor) {
            return (Constructor<?>) executable;
        } else throw new IllegalArgumentException(
                "An executable was not an instance of Constructor<?> and could not be cast"
        );
    }

    static Method getMethod(Executable executable) {
        if (executable instanceof Method) {
            return (Method) executable;
        } else throw new IllegalArgumentException(
                "An executable was not an instance of Method<?> and could not be cast"
        );
    }



}
