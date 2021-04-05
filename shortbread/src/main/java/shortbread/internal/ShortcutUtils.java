package shortbread.internal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

public class ShortcutUtils {

    public static CharSequence getActivityLabel(@NonNull Context context, @NonNull Class<? extends Activity> cls) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getActivityInfo(new ComponentName(context, cls), 0).loadLabel(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private ShortcutUtils() {
    }
}
