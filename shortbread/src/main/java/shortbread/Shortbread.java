package shortbread;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class Shortbread {

    private static Class<?> generated;
    private static Method createShortcuts;
    private static Method callMethodShortcut;
    private static boolean shortcutsSet;
    private static boolean activityLifecycleCallbacksSet;

    @TargetApi(25)
    public static void create(Context context) {
        if (Build.VERSION.SDK_INT < 25) {
            return;
        }

        Context applicationContext = context.getApplicationContext();

        if (generated == null) {
            //noinspection TryWithIdenticalCatches
            try {
                generated = Class.forName("shortbread.ShortbreadGenerated");
                createShortcuts = generated.getMethod("createShortcuts", Context.class);
                callMethodShortcut = generated.getMethod("callMethodShortcut", Activity.class);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

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
    private static void setShortcuts(Context context) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);

        //noinspection TryWithIdenticalCatches
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
            shortcutsSet = true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(14)
    private static void setActivityLifecycleCallbacks(Context applicationContext) {
        ((Application) applicationContext).registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {
                callMethodShortcut(activity);
            }
        });

        activityLifecycleCallbacksSet = true;
    }

    private static void callMethodShortcut(Activity activity) {
        //noinspection TryWithIdenticalCatches
        try {
            callMethodShortcut.invoke(generated, activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Shortbread() {
    }
}
