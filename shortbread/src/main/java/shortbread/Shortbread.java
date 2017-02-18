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
    public static ShortbreadBuilder generate(Context context) {
        final Object returnValue;

        try {
            generated = Class.forName("shortbread.ShortbreadGenerated");
            Method createShortcuts = generated.getMethod("createShortcuts", Context.class);
            returnValue = createShortcuts.invoke(generated, context);
            return new ShortbreadBuilder(context, (List<ShortcutInfo>) returnValue);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

}
