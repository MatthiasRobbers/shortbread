package shortbread;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
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
    private Activity activity;
    @Mock
    private Application application;
    @Mock
    private ShortcutManager shortcutManager;
    @Mock
    private Intent intent;
    @Captor
    ArgumentCaptor<Application.ActivityLifecycleCallbacks> lifecycleCallbacksCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(context.getApplicationContext()).thenReturn(application);
        when(application.getSystemService(ShortcutManager.class)).thenReturn(shortcutManager);
        when(activity.getIntent()).thenReturn(intent);
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
        new Shortbread(context);
        verify(application).registerActivityLifecycleCallbacks(any(Application.ActivityLifecycleCallbacks.class));
    }

    @Test
    public void setsEnabledShortcuts() {
        final List<ShortcutInfo> enabledShortcuts = ShortbreadGenerated.createShortcuts(context).get(0);

        new Shortbread(context);
        verify(shortcutManager).setDynamicShortcuts(enabledShortcuts);
    }

    @Test
    public void setsDisabledShortcuts() {
        final List<ShortcutInfo> disabledShortcuts = ShortbreadGenerated.createShortcuts(activity).get(1);

        new Shortbread(context);
        @SuppressWarnings("rawtypes") ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        //noinspection unchecked
        verify(shortcutManager).disableShortcuts(captor.capture());
        assertEquals(disabledShortcuts.size(), captor.getValue().size());
    }

    @Test
    public void intentDoesNotHaveExtra_doesNotCallMethodShortcut() {
        when(intent.hasExtra("shortbread_method")).thenReturn(false);

        new Shortbread(context);
        ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut = null;

        verify(application).registerActivityLifecycleCallbacks(lifecycleCallbacksCaptor.capture());
        Application.ActivityLifecycleCallbacks lifecycleCallbacks = lifecycleCallbacksCaptor.getValue();

        lifecycleCallbacks.onActivityCreated(activity, null);
        assertNull(ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut);

        lifecycleCallbacks.onActivityStarted(activity);
        assertNull(ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut);
    }

    @Test
    public void activityLifecycleListenerCallsMethodShortcut() {
        when(intent.hasExtra("shortbread_method")).thenReturn(true);

        new Shortbread(context);
        ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut = null;

        verify(application).registerActivityLifecycleCallbacks(lifecycleCallbacksCaptor.capture());
        Application.ActivityLifecycleCallbacks lifecycleCallbacks = lifecycleCallbacksCaptor.getValue();

        lifecycleCallbacks.onActivityCreated(activity, null);
        assertNull(ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut);

        lifecycleCallbacks.onActivityStarted(activity);
        assertEquals(activity, ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut);

        ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut = null;
        lifecycleCallbacks.onActivityStarted(activity);
        assertNull(ShortbreadGenerated.activityThatWasPassedToCallMethodShortcut);
    }
}
