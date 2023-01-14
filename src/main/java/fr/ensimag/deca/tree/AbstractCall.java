package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/*
 * Call of a method, a constructor, or a field via selection
 */
public abstract class AbstractCall extends AbstractExpr {

    private AbstractIdentifier name;

    public AbstractCall(AbstractIdentifier name) {
        Validate.notNull(name);
        this.name = name;
    }

    public AbstractIdentifier getName() {
        return name;
    }

    public Type verifyExprMessage(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, String message) throws ContextualError {
        Type exprType = this.verifyExpr(compiler, localEnv, currentClass);
        if (!exprType.isClass()) {
            throw new ContextualError(message, this.getLocation());
        }
        return exprType;
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub

    }

}
