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
     * Pass 2 of [SyntaxeContextuelle]. Verify that the class methods are OK, without looking at methods bodies.
     */
    public void verifyListDeclMethodMembers(DecacCompiler compiler, ClassDefinition currentClassDef,
            AbstractIdentifier superClass) throws ContextualError {

        for (AbstractDeclMethod myMethod : this.getList()) {
            myMethod.verifyEnvMethod(compiler, currentClassDef, superClass);
        }
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that the class methods are OK, includes looking at methods bodies.
     */
    public void verifyListDeclMethodBody(DecacCompiler compiler, ClassDefinition currentClassDef)
            throws ContextualError {

        for (AbstractDeclMethod myMethod : this.getList()) {
            myMethod.verifyBodyMethod(compiler, currentClassDef);
        }
    }

}
