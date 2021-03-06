package shortbread;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates app shortcuts from {@code @Shortcut} annotations, {@link #create(Context)} is all that needs to be called.
 */
public final class Shortbread {

    private static Class<?> generated;
    private static Method createShortcuts;
    private static Method callMethodShortcut;
    @VisibleForTesting
    static boolean shortcutsSet;
    @VisibleForTesting
    static boolean activityLifecycleCallbacksSet;
    private static final String TAG = "Shortbread";

    /**
     * Publishes the shortcuts created from activity class annotations and activity method annotations in the project.
     * It will also set an activity lifecycle listener to call an annotated activity method when an activity is started
     * with a method shortcut.
     *
     * @param context Any context, the implementation will use the application context.
     */
    @TargetApi(25)
    public static void create(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 25) {
            return;
        }

        if (generated == null) {
            try {
                generated = Class.forName("shortbread.ShortbreadGenerated");
                createShortcuts = generated.getMethod("createShortcuts", Context.class);
                callMethodShortcut = generated.getMethod("callMethodShortcut", Activity.class);
            } catch (ClassNotFoundException e) {
                if (ShortcutUtils.isDebuggable(context)) {
                    Log.d(TAG, "No shortcuts found");
                }
            } catch (NoSuchMethodException e) {
                if (ShortcutUtils.isDebuggable(context)) {
                    Log.d(TAG, "Error generating shortcuts", e);
                }
            }
        }

        Context applicationContext = context.getApplicationContext();

        if (!shortcutsSet) {
            setShortcuts(applicationContext);
        }

        if (!activityLifecycleCallbacksSet) {
            setActivityLifecycleCallbacks(applicationContext);
        }

        if (context instanceof Activity) {
            callMethodShortcut((Activity) context);
        }
    }

    @RequiresApi(25)
    private static void setShortcuts(@NonNull Context context) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);

        if (createShortcuts == null) {
            shortcutManager.removeAllDynamicShortcuts();
        } else {
            try {
                final Object returnValue = createShortcuts.invoke(generated, context);
                @SuppressWarnings("unchecked")
                List<List<ShortcutInfo>> shortcuts = (List<List<ShortcutInfo>>) returnValue;
                List<ShortcutInfo> enabledShortcuts = shortcuts.get(0);
                List<String> disabledShortcutsIds = new ArrayList<>();
                for (final ShortcutInfo shortcutInfo : shortcuts.get(1)) {
                    disabledShortcutsIds.add(shortcutInfo.getId());
                }
                shortcutManager.disableShortcuts(disabledShortcutsIds);
                shortcutManager.setDynamicShortcuts(enabledShortcuts);
            } catch (IllegalAccessException e) {
                if (ShortcutUtils.isDebuggable(context)) {
                    Log.d(TAG, "Error setting shortcuts", e);
                }
            } catch (InvocationTargetException e) {
                if (ShortcutUtils.isDebuggable(context)) {
                    Log.d(TAG, "Error setting shortcuts", e);
                }
            }
        }

        shortcutsSet = true;
    }

    @RequiresApi(14)
    private static void setActivityLifecycleCallbacks(@NonNull Context applicationContext) {
        ((Application) applicationContext).registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {

            private Class<? extends Activity> createdActivityClass;

            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                createdActivityClass = activity.getClass();
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if (activity.getClass() == createdActivityClass) {
                    callMethodShortcut(activity);
                    createdActivityClass = null;
                }
            }
        });

        activityLifecycleCallbacksSet = true;
    }

    private static void callMethodShortcut(@NonNull Activity activity) {
        if (callMethodShortcut == null || !activity.getIntent().hasExtra("shortbread_method")) {
            return;
        }

        try {
            callMethodShortcut.invoke(generated, activity);
        } catch (IllegalAccessException e) {
            if (ShortcutUtils.isDebuggable(activity)) {
                Log.d(TAG, "Error calling shortcuts", e);
            }
        } catch (InvocationTargetException e) {
            if (ShortcutUtils.isDebuggable(activity)) {
                Log.d(TAG, "Error calling shortcuts", e);
            }
        }
    }

    private Shortbread() {
    }
}
