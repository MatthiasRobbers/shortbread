package shortbread.internal;

import android.app.Activity;

public interface MethodShortcuts<T extends Activity> {

    Class getActivityClass();

    void callMethodShortcut(Activity activity, String methodName);
}
