package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.BRA;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Full if/else if/else statement.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class IfThenElse extends AbstractInst {

    private final AbstractExpr condition;
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {

        this.condition.verifyCondition(compiler, localEnv, currentClass);

        this.thenBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
        this.elseBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        int nActual = compiler.getN() +1;
        condition.codeGenInst(compiler);
        compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.getR(nActual)));
        Label labelIf = new Label("If" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        Label labelElse = new Label("Else"+this.getLocation().getLine() + this.getLocation().getPositionInLine());
        Label labelFin = new Label("Fin" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        compiler.addInstruction(new BNE(labelElse));
        compiler.addLabel(labelIf);
        compiler.setN(nActual - 1);
        thenBranch.codeGenListInst(compiler);
        compiler.addInstruction(new BRA(labelFin));
        compiler.addLabel(labelElse);
        elseBranch.codeGenListInst(compiler);
        compiler.addLabel(labelFin);
        compiler.setN(nActual - 1);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("if ");
        condition.decompile(s);
        s.println(" {");
        s.indent();
        thenBranch.decompile(s);
        s.unindent();
        s.println("}");
        s.println("else {");
        s.indent();
        elseBranch.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
