package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * List of fields' declaration
 */
public class ListDeclField extends TreeList<AbstractDeclField> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclField c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

}
