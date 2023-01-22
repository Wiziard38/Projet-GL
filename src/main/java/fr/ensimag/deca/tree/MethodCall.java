package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperBSR;
import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperOffset;
import fr.ensimag.superInstructions.SuperPUSH;
import fr.ensimag.superInstructions.SuperSUBSP;

/*
 * Call of a method function on an expression
 */
public class MethodCall extends AbstractExpr {
    private static final Logger LOG = Logger.getLogger(MethodCall.class);

    private AbstractExpr expr;
    private AbstractIdentifier name;
    private ListExpr args;

    public MethodCall(AbstractExpr e, AbstractIdentifier name, ListExpr args) {
        Validate.notNull(e);
        Validate.notNull(name);
        this.expr = e;
        this.name = name;
        this.args = args;
    }

    public MethodCall(AbstractIdentifier name, ListExpr args) {
        Validate.notNull(name);
        this.expr = null;
        this.name = name;
        this.args = args;
    }

    public ListExpr getArgs() {
        return args;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        ClassType callerClass;
        if (this.expr != null) {
            // On verifie que l'expr est de type class
            Type exprType = expr.verifyExpr(compiler, localEnv, currentClass);
            if (!exprType.isClass()) {
                throw new ContextualError(String.format("L'appel méthode '%s' doit se faire sur une class",
                        this.name), this.getLocation()); // Rule 3.71
            }
            callerClass = exprType.asClassType(null, null);
        } else {
            if (currentClass == null) {
                throw new ContextualError("L'appel méthode avec 'this.' implicite en dehors d'une class impossible",
                        this.getLocation()); // Rule 3.71
            }
            callerClass = currentClass.getType();
        }

        // On verifie que le ident est bien une méthode
        MethodDefinition methodDef = this.name.verifyDefinition(compiler, callerClass.getDefinition().getMembers())
                .asMethodDefinition(String.format("'%s' n'est pas une méthode", this.name), getLocation()); // Rule 3.72

        // On verifie que la signature correspond
        this.verifyRValueStar(compiler, localEnv, currentClass, methodDef.getSignature());

        this.setType(methodDef.getType());
        return methodDef.getType();
    }

    public void verifyRValueStar(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Signature sig) throws ContextualError {

        LOG.debug("Signature: " + sig);
        LOG.debug("Caller: " + this.args);

        int index = 0;
        if (this.args != null) {
            for (AbstractExpr currentExpr : this.args.getList()) {
                currentExpr.verifyRValue(compiler, localEnv, currentClass, sig.paramNumber(index));
                index++;
            }
        }

        if (index != sig.size()) {
            throw new ContextualError("Le nombre de paramètres ne correspond pas",
                    this.getLocation()); // Rule 3.73 // Rule 3.74
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (expr != null) {
            expr.decompile(s);
            s.print(".");
        }
        this.name.decompile(s);
        s.print("(");
        int count = getArgs().size();
        for (AbstractExpr e : getArgs().getList()) {
            e.decompile(s);
            if (count != 1) {
                s.print(", ");
            }
            count--;
        }
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        if (expr != null) {
            expr.prettyPrint(s, prefix, false);
        }
        if (args != null) {
            name.prettyPrint(s, prefix, false);
            args.prettyPrint(s, prefix, true);
        } else {
            name.prettyPrint(s, prefix, true);
        }
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        if (expr != null) {
            expr.iter(f);
        }
        name.iter(f);
        if (args != null) {
            args.iter(f);
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
        int nLeft = compiler.getN() + 1;
        if (this.expr == null) {
            compiler.addInstruction(
                    SuperLOAD.main(SuperOffset.main(-2, Register.LB, compiler.compileInArm()), Register.getR(nLeft),
                            compiler.compileInArm()));
        } else {
            this.expr.codeGenInst(compiler, name);
        }
        compiler.setN(nLeft);
        for (AbstractExpr expr : args.getList()) {
            int nActual = compiler.getN() + 1;
            expr.codeGenInst(compiler, name);
            compiler.addInstruction(SuperPUSH.main(Register.getR(nActual), compiler.compileInArm()));
            compiler.setSP(compiler.getSP() + 1);
            compiler.setN(nActual - 1);
        }
        compiler.addInstruction(SuperPUSH.main(Register.getR(nLeft), compiler.compileInArm()));
        compiler.addInstruction(
                SuperLOAD.main(SuperOffset.main(0, Register.getR(nLeft), compiler.compileInArm()), Register.getR(nLeft),
                        compiler.compileInArm()));
        compiler.addInstruction(
                SuperBSR.main(
                        SuperOffset.main(this.name.getMethodDefinition().getIndex(), Register.getR(nLeft),
                                compiler.compileInArm()),
                        compiler.compileInArm()));
        compiler.addInstruction(SuperSUBSP.main(args.size() + 1, compiler.compileInArm()));
        compiler.addInstruction(SuperLOAD.main(Register.R0, Register.getR(nLeft), compiler.compileInArm()));
        compiler.setN(nLeft);
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
        // TODO Auto-generated method stub

    }
}
