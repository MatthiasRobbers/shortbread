package shortbread;

import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Annotate activities and public activities. Each annotation generates a shortcut, which is customizable with the
 * elements, of that only {@link #id()} is required.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Shortcut {

    /**
     * Unique String identifier for the shortcut. This is the only required element of the annotation because you need
     * id if you want to disable a previously generated shortcut that was pinned by the user.
     */
    String id();

    /**
     * Short label string resource ID. This is the recommended element to set the short label.
     */
    @StringRes int shortLabelRes() default 0;

    /**
     * Alternative element to set the short label directly with a String.
     */
    String shortLabel() default "";

    /**
     * Long label string resource ID. This is the recommended element to set the short label.
     */
    @StringRes int longLabelRes() default 0;

    /**
     * Alternative element to set the long label directly with a String.
     */
    String longLabel() default "";

    /**
     * Drawable icon resource ID for the shortcut icon. If you need an appropriate icon, use Roman Nurik's
     * <a href="https://romannurik.github.io/AndroidAssetStudio/icons-app-shortcut.html">App shortcut icon
     * generator</a>.
     */
    @DrawableRes int icon() default 0;

    /**
     * Message that appears when a disabled shortcut is tapped. Set as a string resource ID. This is the recommended
     * element to set the disabled message.
     */
    @StringRes int disabledMessageRes() default 0;

    /**
     * Alternative element to set the disabled message directly with a String.
     */
    String disabledMessage() default "";

    /**
     * Non-negative, sequential value that is relative to other shortcuts and determines the order of the shortcut list.
     */
    @IntRange(from = 0) int rank() default 0;

    /**
     * If the shortcut is enabled, true by default.
     */
    boolean enabled() default true;

    /**
     * Array of activities that will be launched additionally to the actual shortcut activity. The last activity in the
     * array will be the first to appear after a back press, the second last will appear after the second back press and
     * so on.
     */
    Class[] backStack() default {};

    /**
     * The launcher activity to which the shortcut will be attached. Default is the first launcher activity in the
     * manifest.
     */
    Class activity() default void.class;

    /**
     * The action of the shortcut's intent, which can be used to identify the shortcut in the launched activity.
     */
    String action() default "";
}
