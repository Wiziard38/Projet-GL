package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

public abstract class AbstractDeclField extends Tree {

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub

    }

    /**
     * TODO
     */    
    public abstract void verifyEnvField(DecacCompiler compiler, ClassDefinition currentClassDef, 
            AbstractIdentifier superClass) throws ContextualError;


    /**
     * TODO
     */    
    public abstract void verifyInitField(DecacCompiler compiler, ClassDefinition currentClassDef) 
            throws ContextualError;
}
