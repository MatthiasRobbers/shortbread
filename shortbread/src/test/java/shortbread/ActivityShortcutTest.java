package shortbread;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import javax.tools.JavaFileObject;

import shortbread.util.JavaFileObjectsPatched;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

public class ActivityShortcutTest {

    @Test
    public void simpleShortcutActivity() {
        JavaFileObject source = JavaFileObjectsPatched.forResource("SimpleShortcutActivity.java");
        JavaFileObject generated = JavaFileObjectsPatched.forResource("SimpleShortcutActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void advancedShortcutActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjectsPatched.forResource("AdvancedShortcutActivity.java"),
                JavaFileObjectsPatched.forResource("R.java")
        );
        JavaFileObject generated = JavaFileObjectsPatched.forResource("AdvancedShortcutActivityGenerated.java");
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void twoShortcutActivities() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjectsPatched.forResource("SimpleShortcutActivity.java"),
                JavaFileObjectsPatched.forResource("AdvancedShortcutActivity.java"),
                JavaFileObjectsPatched.forResource("R.java")
        );
        JavaFileObject generated = JavaFileObjectsPatched.forResource("TwoShortcutActivitiesGenerated.java");
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void backstackShortcutActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjectsPatched.forResource("EmptyActivity1.java"),
                JavaFileObjectsPatched.forResource("EmptyActivity2.java"),
                JavaFileObjectsPatched.forResource("BackStackShortcutActivity.java")
        );
        JavaFileObject generated = JavaFileObjectsPatched.forResource("BackStackShortcutActivityGenerated.java");
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void resourcesShortcutActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjectsPatched.forResource("ResourcesShortcutActivity.java"),
                JavaFileObjectsPatched.forResource("R.java")
        );
        JavaFileObject generated = JavaFileObjectsPatched.forResource("ResourcesShortcutActivityGenerated.java");
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void disabledShortcutActivity() {
        JavaFileObject source = JavaFileObjectsPatched.forResource("DisabledShortcutActivity.java");
        JavaFileObject generated = JavaFileObjectsPatched.forResource("DisabledShortcutActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void targetShortcutActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjectsPatched.forResource("EmptyActivity1.java"),
                JavaFileObjectsPatched.forResource("TargetShortcutActivity.java")
        );
        JavaFileObject generated = JavaFileObjectsPatched.forResource("TargetShortcutActivityGenerated.java");
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }
}
