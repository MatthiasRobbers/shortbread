package shortbread;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.support.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public final class Shortbread {

    static Class<?> generated;

    @TargetApi(25)
    @NonNull
    public static ShortbreadInitialBuilder generate(Context context) {
        final Object returnValue;

        try {
            generated = Class.forName("shortbread.ShortbreadGenerated");
            Method createShortcuts = generated.getMethod("createShortcuts", Context.class);
            returnValue = createShortcuts.invoke(generated, context);
            boolean[] shortcutStatus = new boolean[((List<ShortcutInfo>) returnValue).size()];
            // enable all shortcuts by default
            for (int i = 0; i < shortcutStatus.length; i++) {
                shortcutStatus[i] = true;
            }
            return new ShortbreadInitialBuilder(context, (List<ShortcutInfo>) returnValue, shortcutStatus);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

}
