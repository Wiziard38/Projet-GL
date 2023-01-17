package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperLOAD;

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
        // Already verified non void for return type

        // On set si jamais y'a un ConvFloat a ajouter
        this.setExpression(this.expr.verifyRValue(compiler, localEnv, currentClass, returnType));
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        int nActual = compiler.getN() + 1;
        expr.codeGenInst(compiler);
        compiler.addInstruction(SuperLOAD.main(Register.getR(nActual),Register.R0, compiler.compileInArm()));
        
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
