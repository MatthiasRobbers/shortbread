package shortbread.util;

import com.google.testing.compile.JavaFileObjects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.tools.JavaFileObject;

public class JavaFileObjectsPatched {

    /*
    Workaround for https://github.com/google/compile-testing/issues/190
     */
    public static JavaFileObject forResource(String resourceName) {
        try {
            URL url = Files.find(Paths.get(""), Integer.MAX_VALUE, (path, basicFileAttributes) ->
                    path.toString().contains("sourceFolderJavaResources") && path.endsWith(resourceName))
                    .findFirst()
                    .orElseThrow(FileNotFoundException::new)
                    .toUri().toURL();
            return JavaFileObjects.forResource(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
