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

class Creator {

    private static boolean activityLifecycleCallbacksSet;
    private static boolean shortcutsSet;


    @TargetApi(25)
    static void create(Context context, List<ShortcutInfo> allShortcuts, boolean[] shortCutStatus) {
        if (Build.VERSION.SDK_INT < 25) {
            return;
        }

        Context applicationContext = context.getApplicationContext();

        if (!shortcutsSet) {
            setShortcuts(applicationContext, allShortcuts, shortCutStatus);
        }

        if (!activityLifecycleCallbacksSet) {
            setActivityLifecycleCallbacks(applicationContext);
        }

        if (context instanceof Activity) {
            callMethodShortcut((Activity) context);
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

    @RequiresApi(25)
    private static void setShortcuts(Context context, List<ShortcutInfo> allShortcuts, boolean[] shortcutStates) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);

        //noinspection TryWithIdenticalCatches
        shortcutManager.removeAllDynamicShortcuts();
        shortcutManager.setDynamicShortcuts(allShortcuts);
        List<String> disabledShortcuts = new ArrayList<>();
        for (int i = 0; i < shortcutStates.length; i++) {
            if (!shortcutStates[i]) {
                disabledShortcuts.add(allShortcuts.get(i).getId());
            }
        }
        shortcutManager.disableShortcuts(disabledShortcuts);
        shortcutsSet = true;
    }

    private static void callMethodShortcut(Activity activity) {
        //noinspection TryWithIdenticalCatches
        try {
            Method callMethodShortcut = Shortbread.generated.getMethod("callMethodShortcut", Activity.class);

            callMethodShortcut.invoke(Shortbread.generated, activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
