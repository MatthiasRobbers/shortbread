package shortbread;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import java.util.Collections;
import java.util.List;

public class ShortbreadInitializer implements Initializer<Shortbread> {

    @NonNull
    @Override
    public Shortbread create(@NonNull Context context) {
        return new Shortbread(context);
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
