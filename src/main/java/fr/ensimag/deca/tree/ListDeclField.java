package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
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
