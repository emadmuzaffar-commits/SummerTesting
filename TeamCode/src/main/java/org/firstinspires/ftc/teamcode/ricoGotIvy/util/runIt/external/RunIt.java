package org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external;


import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.internal.CallTime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RunIt {
    CallTime callTime() default CallTime.INIT;
}
