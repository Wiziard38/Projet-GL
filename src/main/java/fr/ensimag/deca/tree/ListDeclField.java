package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListDeclField extends TreeList<AbstractDeclField> {

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub

    }

    /**
     * TODO
     */    
    public void verifyListDeclField(DecacCompiler compiler, AbstractIdentifier currentClass, AbstractIdentifier superClass)
            throws ContextualError {
        
        for (AbstractDeclField myDeclField : this.getList()) {
            myDeclField.verifyEnvField(compiler, currentClass, superClass);
        }
    }

}
