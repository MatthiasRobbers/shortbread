package shortbread.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

@SuppressWarnings({"unused", "RedundantSuppression"})
class ShortbreadPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        target.getPluginManager().apply("com.jakewharton.butterknife");
    }
}
