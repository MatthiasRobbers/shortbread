package shortbread;

import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import java.util.Arrays;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

public class MethodShortcutTest {

    @Test
    public void simpleMethodShortcutActivity() {
        JavaFileObject source = JavaFileObjects.forResource("MethodShortcutActivity.java");
        JavaFileObject generated = JavaFileObjects.forResource("MethodShortcutActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void twoMethodShortcutsInOneActivity() {
        JavaFileObject source = JavaFileObjects.forResource("TwoMethodShortcutsActivity.java");
        JavaFileObject generated = JavaFileObjects.forResource("TwoMethodShortcutsActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void twoMethodShortcutActivities() {
        JavaFileObject source1 = JavaFileObjects.forResource("MethodShortcutActivity.java");
        JavaFileObject source2 = JavaFileObjects.forResource("MethodShortcutActivity2.java");
        JavaFileObject generated = JavaFileObjects.forResource("TwoMethodShortcutActivitiesGenerated.java");
        assertAbout(javaSources()).that(Arrays.asList(source1, source2))
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }
}
