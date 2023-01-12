package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * List of parameters' declaration
 */
public class ListDeclParam extends TreeList<AbstractDeclParam> {

    @Override
    public void decompile(IndentPrintStream s) {
        int count = getList().size();
        for (AbstractDeclParam p : getList()) {
            p.decompile(s);
            if (count != 1) {
                s.print(", ");
            }
            count--;
        }
    }

}
