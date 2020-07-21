package shortbread;

import org.junit.Test;

import java.util.Arrays;

import javax.tools.JavaFileObject;

import shortbread.util.JavaFileObjectsPatched;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

public class MethodShortcutTest {

    @Test
    public void simpleMethodShortcutActivity() {
        JavaFileObject source = JavaFileObjectsPatched.forResource("MethodShortcutActivity.java");
        JavaFileObject generated = JavaFileObjectsPatched.forResource("MethodShortcutActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void twoMethodShortcutsInOneActivity() {
        JavaFileObject source = JavaFileObjectsPatched.forResource("TwoMethodShortcutsActivity.java");
        JavaFileObject generated = JavaFileObjectsPatched.forResource("TwoMethodShortcutsActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void twoMethodShortcutActivities() {
        JavaFileObject source1 = JavaFileObjectsPatched.forResource("MethodShortcutActivity.java");
        JavaFileObject source2 = JavaFileObjectsPatched.forResource("MethodShortcutActivity2.java");
        JavaFileObject generated = JavaFileObjectsPatched.forResource("TwoMethodShortcutActivitiesGenerated.java");
        assertAbout(javaSources()).that(Arrays.asList(source1, source2))
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }
}
