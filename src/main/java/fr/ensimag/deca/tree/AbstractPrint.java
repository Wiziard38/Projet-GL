package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Print statement (print, println, ...).
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractPrint extends AbstractInst {
    private static final Logger LOG = Logger.getLogger(AbstractPrint.class);

    private boolean printHex;
    private ListExpr arguments = new ListExpr();

    abstract String getSuffix();

    public AbstractPrint(boolean printHex, ListExpr arguments) {
        Validate.notNull(arguments);
        this.arguments = arguments;
        this.printHex = printHex;
    }

    public ListExpr getArguments() {
        return arguments;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        Validate.notNull(localEnv);

        for (AbstractExpr myExpr : this.arguments.getList()) {
            LOG.debug(myExpr);
            Type myExprType = myExpr.verifyExpr(compiler, localEnv, currentClass);
            if (!myExprType.isFloat() && !myExprType.isInt() && !myExprType.isString()) {

                throw new ContextualError(String.format("Print ne peut pas afficher une expression de type '%s'",
                        myExprType.toString()), myExpr.getLocation()); // Rule 3.31
            }
        }
    }

    /**
     * Generation du code de la liste d'instructions
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param name le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
        for (AbstractExpr a : getArguments().getList()) {
            a.codeGenPrint(compiler, printHex, name);
        }
    }

    public boolean getPrintHex() {
        return printHex;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("print" + getSuffix());
        if (printHex) {
            s.print("x");
        }
        s.print("(");
        this.arguments.decompile(s);
        s.print(");");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        arguments.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        arguments.prettyPrint(s, prefix, true);
    }

}
