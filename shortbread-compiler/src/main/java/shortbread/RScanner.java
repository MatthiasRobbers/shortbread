package shortbread;

import androidx.annotation.Nullable;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeScanner;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Copied and stripped from Butter Knife, see
 * https://github.com/JakeWharton/butterknife/blob/master/butterknife-compiler/src/main/java/butterknife/compiler/ButterKnifeProcessor.java
 */
class RScanner extends TreeScanner {

    final Map<Integer, Id> resourceIds = new LinkedHashMap<>();

    @Override
    public void visitSelect(JCTree.JCFieldAccess jcFieldAccess) {
        Symbol symbol = jcFieldAccess.sym;
        Id id = parseId(symbol);
        if (id != null) {
            resourceIds.put(id.value, id);
        }
    }

    @Nullable
    private Id parseId(Symbol symbol) {
        Id id = null;
        if (symbol.getEnclosingElement() != null
                && symbol.getEnclosingElement().getEnclosingElement() != null
                && symbol.getEnclosingElement().getEnclosingElement().enclClass() != null) {
            try {
                int value = (Integer) requireNonNull(((Symbol.VarSymbol) symbol).getConstantValue());
                id = new Id(value, symbol);
            } catch (Exception ignored) {
            }
        }
        return id;
    }

    void reset() {
        resourceIds.clear();
    }
}
