package shortbread;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 25, manifest = Config.NONE)
@TargetApi(25)
public class ShortbreadTest {

    @Mock
    private Activity activity;
    @Mock
    private Application application;
    @Mock
    private ShortcutManager shortcutManager;

    @Before
    public void setUp() {
        Shortbread.shortcutsSet = false;
        Shortbread.activityLifecycleCallbacksSet = false;

        MockitoAnnotations.initMocks(this);
        when(activity.getApplicationContext()).thenReturn(application);
        when(application.getSystemService(ShortcutManager.class)).thenReturn(shortcutManager);
    }

    @Test
    @Config(sdk = 24)
    public void doesNothingBeforeApi25() {
        Shortbread.create(activity);
        verifyNoMoreInteractions(activity);
    }

    @Test
    public void usesApplicationContext() {
        Shortbread.create(activity);
        verify(activity).getApplicationContext();
        verify(application).getSystemService(ShortcutManager.class);
    }

    @Test
    public void registersActivityLifecycleCallbacksOnce() {
        Shortbread.create(activity);
        verify(application).registerActivityLifecycleCallbacks(any(Application.ActivityLifecycleCallbacks.class));

        Shortbread.create(activity);
        verify(application, times(1)).registerActivityLifecycleCallbacks(any(Application.ActivityLifecycleCallbacks.class));
    }

    @Test
    public void setsEnabledShortcutsOnce() {
        final List<ShortcutInfo> enabledShortcuts = ShortbreadGenerated.createShortcuts(activity).get(0);

        Shortbread.create(activity);
        verify(shortcutManager).setDynamicShortcuts(enabledShortcuts);

        Shortbread.create(activity);
        verify(shortcutManager, times(1)).setDynamicShortcuts(enabledShortcuts);
    }

    @Test
    public void setsDisabledShortcutsOnce() {
        final List<ShortcutInfo> disabledShortcuts = ShortbreadGenerated.createShortcuts(activity).get(1);

        Shortbread.create(activity);
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        //noinspection unchecked
        verify(shortcutManager).disableShortcuts(captor.capture());
        assertEquals(disabledShortcuts.size(), captor.getValue().size());

        Shortbread.create(activity);
        //noinspection unchecked
        verify(shortcutManager, times(1)).disableShortcuts(any(List.class));
    }

    @Test
    public void callsMethodShortcut() {
        ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut = null;
        Shortbread.create(activity);
        assertEquals(activity, ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut);
    }

    @Test
    public void activityLifecycleListenerCallsMethodShortcut() {
        Shortbread.create(activity);
        ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut = null;

        ArgumentCaptor<Application.ActivityLifecycleCallbacks> captor = ArgumentCaptor.forClass(
                Application.ActivityLifecycleCallbacks.class);
        verify(application).registerActivityLifecycleCallbacks(captor.capture());
        Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = captor.getValue();
        activityLifecycleCallbacks.onActivityCreated(activity, null);
        assertEquals(activity, ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut);
    }
}
