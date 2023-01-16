package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * List of methods' declaration
 */
public class ListDeclMethod extends TreeList<AbstractDeclMethod> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclMethod m : getList()) {
            s.println();
            m.decompile(s);
            s.println();
        }
    }

    /**
     * TODO
     */    
    public void verifyListDeclMethodMembers(DecacCompiler compiler, ClassDefinition currentClassDef, 
            AbstractIdentifier superClass) throws ContextualError {
        
        for (AbstractDeclMethod myMethod : this.getList()) {
            myMethod.verifyEnvMethod(compiler, currentClassDef, superClass);
        }
    }

    /**
     * TODO
     * @param compiler
     * @param currentClass
     * @param superClass
     * @throws ContextualError
     */
    public void verifyListDeclMethodBody(DecacCompiler compiler, ClassDefinition currentClassDef)
            throws ContextualError {
        
        for (AbstractDeclMethod myMethod : this.getList()) {
            myMethod.verifyBodyMethod(compiler, currentClassDef);
        }
    }

    protected void codeGenListMethode(DecacCompiler compiler, AbstractIdentifier className){
        for(AbstractDeclMethod method : this.getList()){
            method.codeGenDeclMethod(compiler, className);
        }
    }

}
