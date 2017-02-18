package shortbread;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Shortcut {

    @IdRes
    int id();

    String shortLabel() default "";

    @StringRes int shortLabelRes() default 0;

    String longLabel() default "";

    @StringRes int longLabelRes() default 0;

    @DrawableRes int icon() default 0;

    String disabledMessage() default "";

    @StringRes int disabledMessageRes() default 0;

    @IntRange(from = 0) int rank() default 0;

    boolean enabled() default true;

    Class[] backStack() default {};

    Class activity() default void.class;

    String action() default "";
}
