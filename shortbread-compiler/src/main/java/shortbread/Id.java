package shortbread;

import androidx.annotation.Nullable;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.sun.tools.javac.code.Symbol;

/**
 * Represents an ID of an Android resource.
 * <p>
 * Adapted from Butter Knife, see
 * https://github.com/JakeWharton/butterknife/blob/master/butterknife-compiler/src/main/java/butterknife/compiler/Id.java
 */
final class Id {

    private static final String R = "R";

    final int value;
    final CodeBlock code;
    final String resourceName;
    final String type;

    Id(int value, @Nullable Symbol rSymbol) {
        this.value = value;
        if (rSymbol != null) {
            ClassName className = ClassName.get(rSymbol.packge().getQualifiedName().toString(), R,
                    rSymbol.enclClass().name.toString());
            this.type = rSymbol.enclClass().name.toString();
            this.resourceName = rSymbol.name.toString();

            this.code = CodeBlock.of("$T.$N", className, resourceName);
        } else {
            this.type = null;
            this.resourceName = null;
            this.code = CodeBlock.of("$L", value);
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Id && value == ((Id) o).value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return R + "." + type + "." + resourceName;
    }
}