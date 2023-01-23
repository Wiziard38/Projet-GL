package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperBNE;
import fr.ensimag.superInstructions.SuperBRA;
import fr.ensimag.superInstructions.SuperCMP;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class While extends AbstractInst {
    private AbstractExpr condition;
    private ListInst body;

    public AbstractExpr getCondition() {
        return condition;
    }

    public ListInst getBody() {
        return body;
    }

    public While(AbstractExpr condition, ListInst body) {
        Validate.notNull(condition);
        Validate.notNull(body);
        this.condition = condition;
        this.body = body;
    }

    /**
     * Genère le code d'un while. on génere le code pour tester la conditions, ensuite les instructions et on boucle sur la condition.
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param nameBloc le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
        Label labelCondition = new Label(
                "ConditionWhile" + this.getLocation().getLine() + this.getLocation().getPositionInLine());
        Label labelFin = new Label("FinWhile" + this.getLocation().getLine() + this.getLocation().getPositionInLine());

        int nCondition = compiler.getN() + 1;
        compiler.addLabel(labelCondition);
        this.getCondition().codeGenInst(compiler, name);
        compiler.addInstruction(
                SuperCMP.main(new ImmediateInteger(1), Register.getR(nCondition), compiler.compileInArm()));
        compiler.addInstruction(SuperBNE.main(labelFin, compiler.compileInArm()));
        compiler.setN(nCondition - 1);
        this.getBody().codeGenListInst(compiler, name);
        compiler.addInstruction(SuperBRA.main(labelCondition, compiler.compileInArm()));
        compiler.addLabel(labelFin);
        compiler.setN(nCondition - 1);

    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.condition.verifyCondition(compiler, localEnv, currentClass);

        this.body.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("while (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getBody().decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

}
