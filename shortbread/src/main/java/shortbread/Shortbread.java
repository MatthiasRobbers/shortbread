package shortbread;

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
import androidx.startup.AppInitializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates app shortcuts from {@code @Shortcut} annotations. This class is automatically created by
 * {@link shortbread.ShortbreadInitializer} and sets the shortcuts automatically on app startup. No interaction with
 * this class is required, the only code needed is the annotations.
 */
public final class Shortbread {

    private Class<?> generated;
    private Method createShortcuts;
    private Method callMethodShortcut;
    private static final String TAG = "Shortbread";

    /**
     * Publishes the shortcuts created from activity class annotations and activity method annotations in the project.
     * It will also set an activity lifecycle listener to call an annotated activity method when an activity is started
     * with a method shortcut.
     *
     * @deprecated No need to call this anymore, the library is automatically initialized by {@link shortbread.ShortbreadInitializer}.
     * @param context Any context, the implementation will use the application context.
     */
    @Deprecated
    public static void create(@NonNull Context context) {
        AppInitializer.getInstance(context).initializeComponent(ShortbreadInitializer.class);
    }

    Shortbread(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 25) {
            return;
        }

        if (generated == null) {
            try {
                generated = Class.forName("shortbread.ShortbreadGenerated");
                createShortcuts = generated.getMethod("createShortcuts", Context.class);
            } catch (ClassNotFoundException e) {
                if (ShortcutUtils.isDebuggable(context)) {
                    Log.d(TAG, "No shortcuts found");
                }
            } catch (NoSuchMethodException e) {
                if (ShortcutUtils.isDebuggable(context)) {
                    Log.d(TAG, "Error generating shortcuts", e);
                }
            }

            try {
                callMethodShortcut = generated.getMethod("callMethodShortcut", Activity.class);
            } catch (NoSuchMethodException ignored) {
                // no method shortcuts found
            }
        }

        Context applicationContext = context.getApplicationContext();

        setShortcuts(applicationContext);

        if (callMethodShortcut != null) {
            setActivityLifecycleCallbacks(applicationContext);
        }
    }

    @RequiresApi(25)
    private void setShortcuts(@NonNull Context context) {
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
    }

    @RequiresApi(14)
    private void setActivityLifecycleCallbacks(@NonNull Context applicationContext) {
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
    }

    private void callMethodShortcut(@NonNull Activity activity) {
        if (callMethodShortcut == null || !activity.getIntent().hasExtra("shortbread_method")) {
            return;
        }

        try {
            callMethodShortcut.invoke(generated, activity);
        } catch (IllegalAccessException e) {
            if (ShortcutUtils.isDebuggable(activity)) {
                Log.d(TAG, "Error calling shortcut", e);
            }
        } catch (InvocationTargetException e) {
            if (ShortcutUtils.isDebuggable(activity)) {
                Log.d(TAG, "Error calling shortcut", e);
            }
        }
    }
}
