package shortbread;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

class SimpleActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(@NonNull final Activity activity, final Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull final Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull final Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull final Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull final Activity activity, @NonNull final Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull final Activity activity) {

    }
}
