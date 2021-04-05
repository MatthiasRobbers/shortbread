package shortbread;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;

import com.example.TestMethodShortcutActivity;
import com.example.TestMethodShortcutActivity_Shortcuts;
import com.example.TestShortcutActivity_Shortcuts;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 25, manifest = Config.NONE)
@TargetApi(25)
public class ShortbreadTest {

    @Mock
    private Context context;
    @Mock
    private Application application;
    @Mock
    private PackageManager packageManager;
    @Mock
    private PackageInfo packageInfo;
    @Mock
    private ActivityInfo shortcutActivityInfo;
    @Mock
    private ActivityInfo methodShortcutActivityInfo;
    @Mock
    private ShortcutManager shortcutManager;
    @Mock
    private Intent intent;
    @Captor
    ArgumentCaptor<Application.ActivityLifecycleCallbacks> lifecycleCallbacksCaptor;
    private final TestMethodShortcutActivity activity = new TestMethodShortcutActivity();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(context.getApplicationContext()).thenReturn(application);
        when(context.getPackageName()).thenReturn("com.example");
        when(application.getPackageName()).thenReturn("com.example");
        when(application.getPackageManager()).thenReturn(packageManager);
        when(packageManager.getPackageInfo(anyString(), anyInt())).thenReturn(packageInfo);
        when(application.getSystemService(ShortcutManager.class)).thenReturn(shortcutManager);

        shortcutActivityInfo.name = "com.example.TestShortcutActivity";
        methodShortcutActivityInfo.name = "com.example.TestMethodShortcutActivity";
        packageInfo.activities = new ActivityInfo[]{shortcutActivityInfo, methodShortcutActivityInfo};
        activity.setIntent(intent);

        if (Build.VERSION.SDK_INT > 24) {
            TestShortcutActivity_Shortcuts.shortcutInfos.add(new ShortcutInfo.Builder(context, "ID").build());
        }
    }

    @Test
    @Config(sdk = 24)
    public void doesNothingBeforeApi25() {
        new Shortbread(context);
        verifyNoMoreInteractions(context);
    }

    @Test
    public void usesApplicationContext() {
        new Shortbread(context);
        verify(context).getApplicationContext();
        verify(application).getSystemService(ShortcutManager.class);
    }

    @Test
    public void registersActivityLifecycleCallbacks() {
        packageInfo.activities = new ActivityInfo[]{shortcutActivityInfo};

        new Shortbread(context);
        verify(application, times(0)).registerActivityLifecycleCallbacks(any(Application.ActivityLifecycleCallbacks.class));

        packageInfo.activities = new ActivityInfo[]{shortcutActivityInfo, methodShortcutActivityInfo};

        new Shortbread(context);
        verify(application).registerActivityLifecycleCallbacks(any(Application.ActivityLifecycleCallbacks.class));
    }

    @Test
    public void setsShortcuts() {
        new Shortbread(context);
        verify(shortcutManager).setDynamicShortcuts(TestShortcutActivity_Shortcuts.shortcutInfos);
    }

    @Test
    public void setsDisabledShortcuts() {
        TestShortcutActivity_Shortcuts.disabledShortcutIds.addAll(Arrays.asList("id1", "id2"));

        new Shortbread(context);
        verify(shortcutManager).disableShortcuts(TestShortcutActivity_Shortcuts.disabledShortcutIds);
    }

    @Test
    public void intentDoesNotHaveExtra_doesNotCallMethodShortcut() {
        when(intent.hasExtra("shortbread_method")).thenReturn(false);

        new Shortbread(context);
        TestMethodShortcutActivity_Shortcuts.activityThatWasPassedToCallMethodShortcut = null;

        verify(application).registerActivityLifecycleCallbacks(lifecycleCallbacksCaptor.capture());
        Application.ActivityLifecycleCallbacks lifecycleCallbacks = lifecycleCallbacksCaptor.getValue();

        lifecycleCallbacks.onActivityCreated(activity, null);
        assertNull(TestMethodShortcutActivity_Shortcuts.activityThatWasPassedToCallMethodShortcut);

        lifecycleCallbacks.onActivityStarted(activity);
        assertNull(TestMethodShortcutActivity_Shortcuts.activityThatWasPassedToCallMethodShortcut);
    }

    @Test
    public void activityLifecycleListenerCallsMethodShortcut() {
        when(intent.hasExtra("shortbread_method")).thenReturn(true);

        new Shortbread(context);
        TestMethodShortcutActivity_Shortcuts.activityThatWasPassedToCallMethodShortcut = null;

        verify(application).registerActivityLifecycleCallbacks(lifecycleCallbacksCaptor.capture());
        Application.ActivityLifecycleCallbacks lifecycleCallbacks = lifecycleCallbacksCaptor.getValue();

        lifecycleCallbacks.onActivityCreated(activity, null);
        assertNull(TestMethodShortcutActivity_Shortcuts.activityThatWasPassedToCallMethodShortcut);

        lifecycleCallbacks.onActivityStarted(activity);
        assertEquals(activity, TestMethodShortcutActivity_Shortcuts.activityThatWasPassedToCallMethodShortcut);

        TestMethodShortcutActivity_Shortcuts.activityThatWasPassedToCallMethodShortcut = null;
        lifecycleCallbacks.onActivityStarted(activity);
        assertNull(TestMethodShortcutActivity_Shortcuts.activityThatWasPassedToCallMethodShortcut);
    }
}
