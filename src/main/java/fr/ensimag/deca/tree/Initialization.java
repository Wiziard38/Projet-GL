package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * @author gl39
 * @date 01/01/2023
 */
public class Initialization extends AbstractInitialization {

    public AbstractExpr getExpression() {
        return expression;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        this.getExpression().codeGenInst(compiler);
        compiler.addInstruction(new STORE(Register.getR(compiler.getN()),new RegisterOffset(compiler.getSP()+1, Register.GB)));
        
    }

    private AbstractExpr expression;

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Validate.notNull(t);

        // On set si jamais y'a un ConvFloat a ajouter
        this.setExpression(this.getExpression().verifyRValue(compiler, localEnv, currentClass, t));

        // Type initializationType = this.expression.verifyExpr(compiler, localEnv, currentClass);

        // if (t.isFloat() && initializationType.isInt()) {
        //     ConvFloat newTreeNode = new ConvFloat(this.expression);
        //     newTreeNode.setType(compiler.environmentType.FLOAT);
        //     this.setExpression(newTreeNode);
        // } else if (this.getExpression().verifyExpr(compiler, localEnv, currentClass) != t) {
        //     throw new ContextualError(String.format("Cette expression devrait être de type '%s'", 
        //             t.toString()), this.getLocation()); // Rule 3.28
        // }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expression.decompile(s);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }
}
