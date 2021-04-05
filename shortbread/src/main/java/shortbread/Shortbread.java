package shortbread;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.startup.AppInitializer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import shortbread.internal.MethodShortcuts;
import shortbread.internal.Shortcuts;

/**
 * Creates app shortcuts from {@code @Shortcut} annotations. This class is automatically created by
 * {@link shortbread.ShortbreadInitializer} and sets the shortcuts automatically on app startup. No interaction with
 * this class is required, the only code needed is the annotations.
 */
public final class Shortbread {

    private final List<Shortcuts> shortcutsObjects = new ArrayList<>();
    private final List<MethodShortcuts<? extends Activity>> methodShortcutsObjects = new ArrayList<>();
    private static final String EXTRA_METHOD = "shortbread_method";
    private static final String TAG = "Shortbread";

    /**
     * Publishes the shortcuts created from activity class annotations and activity method annotations in the project.
     * It will also set an activity lifecycle listener to call an annotated activity method when an activity is started
     * with a method shortcut.
     *
     * @param context Any context, the implementation will use the application context.
     * @deprecated No need to call this anymore, the library is automatically initialized by {@link shortbread.ShortbreadInitializer}.
     */
    @Deprecated
    public static void create(@NonNull Context context) {
        AppInitializer.getInstance(context).initializeComponent(ShortbreadInitializer.class);
    }

    Shortbread(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 25) {
            return;
        }

        Context applicationContext = context.getApplicationContext();

        createShortcutsObjects(applicationContext);
        setShortcuts(applicationContext);

        if (containsMethodShortcuts()) {
            setActivityLifecycleCallbacks(applicationContext);
        }
    }

    private void createShortcutsObjects(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activityInfo : packageInfo.activities) {
                try {
                    Class<?> shortcutsClass = Class.forName(activityInfo.name + "_Shortcuts");
                    Shortcuts shortcutsObject = (Shortcuts) shortcutsClass.getConstructor().newInstance();
                    shortcutsObjects.add(shortcutsObject);

                    if (shortcutsObject instanceof MethodShortcuts<?>) {
                        methodShortcutsObjects.add((MethodShortcuts<? extends Activity>) shortcutsObject);
                    }
                } catch (ClassNotFoundException ignored) {
                    // no shortcuts found for that activity
                }
            }
        } catch (NoSuchMethodException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException
                | PackageManager.NameNotFoundException e) {
            if (isDebuggable(context)) {
                Log.w(TAG, e);
            }
        }
    }

    @RequiresApi(25)
    private void setShortcuts(@NonNull Context context) {
        List<ShortcutInfo> enabledShortcuts = new ArrayList<>();
        List<String> disabledShortcutIds = new ArrayList<>();
        for (Shortcuts shortcutsObject : shortcutsObjects) {
            enabledShortcuts.addAll(shortcutsObject.getShortcuts(context));
            disabledShortcutIds.addAll(shortcutsObject.getDisabledShortcutIds());
        }

        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);

        if (enabledShortcuts.isEmpty()) {
            shortcutManager.removeAllDynamicShortcuts();
        } else {
            shortcutManager.setDynamicShortcuts(enabledShortcuts);
        }

        if (!disabledShortcutIds.isEmpty()) {
            shortcutManager.disableShortcuts(disabledShortcutIds);
        }
    }

    @RequiresApi(14)
    private void setActivityLifecycleCallbacks(@NonNull Context applicationContext) {
        ((Application) applicationContext).registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {

            private Class<? extends Activity> createdActivityClass;

            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                if (activity.getIntent().hasExtra(EXTRA_METHOD)) {
                    createdActivityClass = activity.getClass();
                }
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

    private boolean containsMethodShortcuts() {
        for (Shortcuts shortcutsObject : shortcutsObjects) {
            if (shortcutsObject instanceof MethodShortcuts) {
                return true;
            }
        }

        return false;
    }

    private void callMethodShortcut(@NonNull Activity activity) {
        for (MethodShortcuts<? extends Activity> methodShortcutsObject : methodShortcutsObjects) {
            if (methodShortcutsObject.getActivityClass() == activity.getClass()) {
                methodShortcutsObject.callMethodShortcut(activity, activity.getIntent().getStringExtra(EXTRA_METHOD));
                break;
            }
        }
    }

    private boolean isDebuggable(@NonNull Context context) {
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
