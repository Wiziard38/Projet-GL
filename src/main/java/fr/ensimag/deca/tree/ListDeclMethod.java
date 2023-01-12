package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * List of methods' declaration
 */
public class ListDeclMethod extends TreeList<AbstractMethod> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractMethod m : getList()) {
            m.decompile(s);
            s.println();
            s.println();
        }
    }

}
