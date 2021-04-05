package shortbread;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import javax.tools.JavaFileObject;

import shortbread.util.JavaFileObjectsPatched;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

public class ShortcutValidationTest {

    @Test
    public void noActivity() {
        JavaFileObject source = JavaFileObjectsPatched.forResource("validation/NoActivity.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .failsToCompile()
                .withErrorContaining("Only activities can be annotated with @Shortcut");
    }

    @Test
    public void abstractMethod() {
        JavaFileObject source = JavaFileObjectsPatched.forResource("validation/AbstractMethod.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .failsToCompile()
                .withErrorContaining("Methods annotated with @Shortcut must not be abstract");
    }

    @Test
    public void privateMethod() {
        JavaFileObject source = JavaFileObjectsPatched.forResource("validation/PrivateMethod.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .failsToCompile()
                .withErrorContaining("Methods annotated with @Shortcut must not be private");
    }

    @Test
    public void noActivityMethod() {
        JavaFileObject source = JavaFileObjectsPatched.forResource("validation/NoActivityMethod.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .failsToCompile()
                .withErrorContaining("Methods annotated with @Shortcut must be part of activities");
    }

    @Test
    public void noParameterlessMethod() {
        JavaFileObject source = JavaFileObjectsPatched.forResource("validation/NoParameterlessMethod.java");
        assertAbout(javaSource()).that(source)
                .processedWith(new ShortcutProcessor())
                .failsToCompile()
                .withErrorContaining("Methods annotated with @Shortcut can't have parameters");
    }

    @Test
    public void multipleShortLabelsActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjectsPatched.forResource("validation/MultipleShortLabels.java"),
                JavaFileObjectsPatched.forResource("R.java")
        );
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .failsToCompile()
                .withErrorContaining("Only one of shortLabelRes, shortLabelResName and shortLabel can be set");
    }

    @Test
    public void multipleLongLabelsActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjectsPatched.forResource("validation/MultipleLongLabels.java"),
                JavaFileObjectsPatched.forResource("R.java")
        );
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .failsToCompile()
                .withErrorContaining("Only one of longLabelRes, longLabelResName and longLabel can be set");
    }

    @Test
    public void multipleDisabledMessagesActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjectsPatched.forResource("validation/MultipleDisabledMessages.java"),
                JavaFileObjectsPatched.forResource("R.java")
        );
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .failsToCompile()
                .withErrorContaining(
                        "Only one of disabledMessageRes, disabledMessageResName and disabledMessage can be set");
    }

    @Test
    public void multipleIconsActivity() {
        List<JavaFileObject> sources = Arrays.asList(
                JavaFileObjectsPatched.forResource("validation/MultipleIcons.java"),
                JavaFileObjectsPatched.forResource("R.java")
        );
        assertAbout(javaSources()).that(sources)
                .processedWith(new ShortcutProcessor())
                .failsToCompile()
                .withErrorContaining("Only one of icon and iconResName can be set");
    }
}
