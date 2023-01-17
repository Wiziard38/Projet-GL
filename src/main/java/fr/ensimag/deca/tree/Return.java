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
 * Return keyword
 */
public class Return extends AbstractInst {

    private AbstractExpr expr;

    public Return(AbstractExpr e) {
        Validate.notNull(e);
        expr = e;
    }

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expr = expression;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType) throws ContextualError {
        // On verifie si le type de retour n'est pas void
        if (returnType.isVoid()) {
            throw new ContextualError("'Return' ne peut pas être appelé pour une fonction donc le type de retour est void",
                    this.getLocation()); // Rule 
        }
        // On set si jamais y'a un ConvFloat a ajouter
        this.setExpression(this.expr.verifyRValue(compiler, localEnv, currentClass, returnType));
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        // TODO Auto-generated method stub

    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        expr.decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
    }

}
