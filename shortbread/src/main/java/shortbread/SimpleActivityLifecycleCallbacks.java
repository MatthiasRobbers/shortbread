package shortbread;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

@TargetApi(14)
class SimpleActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(final Activity activity) {

    }

    @Override
    public void onActivityResumed(final Activity activity) {

    }

    @Override
    public void onActivityPaused(final Activity activity) {

    }

    @Override
    public void onActivityStopped(final Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(final Activity activity) {

    }
}
