package shortbread;

import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

public class ActivityShortcutTest {

    @Test
    public void simpleShortcutActivity() {
        JavaFileObject source = JavaFileObjects.forResource("SimpleShortcutActivity.java");
        JavaFileObject generated = JavaFileObjects.forResource("SimpleShortcutActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void advancedShortcutActivity() {
        JavaFileObject source = JavaFileObjects.forResource("AdvancedShortcutActivity.java");
        JavaFileObject generated = JavaFileObjects.forResource("AdvancedShortcutActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void twoShortcutActivities() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjects.forResource("SimpleShortcutActivity.java"),
                JavaFileObjects.forResource("AdvancedShortcutActivity.java")
        );
        JavaFileObject generated = JavaFileObjects.forResource("TwoShortcutActivitiesGenerated.java");
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void backstackShortcutActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjects.forResource("EmptyActivity1.java"),
                JavaFileObjects.forResource("EmptyActivity2.java"),
                JavaFileObjects.forResource("BackStackShortcutActivity.java")
        );
        JavaFileObject generated = JavaFileObjects.forResource("BackStackShortcutActivityGenerated.java");
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void resourcesShortcutActivity() {
        JavaFileObject source = JavaFileObjects.forResource("ResourcesShortcutActivity.java");
        JavaFileObject generated = JavaFileObjects.forResource("ResourcesShortcutActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void disabledShortcutActivity() {
        JavaFileObject source = JavaFileObjects.forResource("DisabledShortcutActivity.java");
        JavaFileObject generated = JavaFileObjects.forResource("DisabledShortcutActivityGenerated.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }

    @Test
    public void targetShortcutActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjects.forResource("EmptyActivity1.java"),
                JavaFileObjects.forResource("TargetShortcutActivity.java")
        );
        JavaFileObject generated = JavaFileObjects.forResource("TargetShortcutActivityGenerated.java");
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(generated);
    }
}
