package shortbread.internal;

import android.content.Context;
import android.content.pm.ShortcutInfo;

import java.util.List;

public interface Shortcuts {

    List<ShortcutInfo> getShortcuts(Context context);

    List<String> getDisabledShortcutIds();
}
