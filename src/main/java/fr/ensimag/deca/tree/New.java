package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

import org.apache.commons.lang.Validate;

/*
 * Call a constructor via keyword 'new'
 */
public class New extends AbstractCall {

    public New(AbstractIdentifier name) {
        super(name);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        getName().decompile(s);
        s.print("();");
    }

}
