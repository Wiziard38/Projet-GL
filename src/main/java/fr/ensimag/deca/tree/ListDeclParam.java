package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * List of parameters' declaration
 */
public class ListDeclParam extends TreeList<AbstractDeclParam> {

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO: à améliorer vu qu'il ya une virgule en trop à la fin
        for (AbstractDeclParam p : getList()) {
            p.decompile(s);
            s.print(", ");
        }
    }

}
