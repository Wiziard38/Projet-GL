package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

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

    /**
     * Genère le code d'une initialisation
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param def definition de la variable nécessitant l'initialisation
     * @param name le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler, Definition def, String name) {
        this.getExpression().codeGenInst(compiler, name);
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
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(" = ");
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
