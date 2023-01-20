package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.ImmediateFloat;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperWINT;
import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperWFLOAT;
import fr.ensimag.superInstructions.SuperWFLOATX;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Expression, i.e. anything that has a value.
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractExpr extends AbstractInst {
    private static final Logger LOG = Logger.getLogger(AbstractExpr.class);

    /**
     * @return true if the expression does not correspond to any concrete token
     *         in the source code (and should be decompiled to the empty string).
     */
    boolean isImplicit() {
        return false;
    }

    /**
     * Get the type decoration associated to this expression (i.e. the type computed
     * by contextual verification).
     */
    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        Validate.notNull(type);
        this.type = type;
    }

    private Type type;

    /**
     * Verify the expression for contextual error.
     * 
     * implements non-terminals "expr" and "lvalue"
     * of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler     (contains the "env_types" attribute)
     * @param localEnv
     *                     Environment in which the expression should be checked
     *                     (corresponds to the "env_exp" attribute)
     * @param currentClass
     *                     Definition of the class containing the expression
     *                     (corresponds to the "class" attribute)
     *                     is null in the main bloc.
     * @return the Type of the expression
     *         (corresponds to the "type" attribute)
     */
    public abstract Type verifyExpr(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Verify the expression in right hand-side of (implicit) assignments
     * 
     * implements non-terminal "rvalue" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler     contains the "env_types" attribute
     * @param localEnv     corresponds to the "env_exp" attribute
     * @param currentClass corresponds to the "class" attribute
     * @param expectedType corresponds to the "type1" attribute
     * @return this with an additional ConvFloat if needed...
     */
    public AbstractExpr verifyRValue(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass,
            Type expectedType)
            throws ContextualError {
        Type exprType = this.verifyExpr(compiler, localEnv, currentClass);
        LOG.debug(exprType);

        // Vérification de assign_compatible
        if (expectedType.isFloat() && exprType.isInt()) {
            ConvFloat newTreeNode = new ConvFloat(this);
            newTreeNode.verifyExpr(compiler, localEnv, currentClass);
            return newTreeNode;
        }

        if (exprType.subType(expectedType)) {
            return this;
        }
        
        throw new ContextualError(String.format("Cette expression devrait être de type '%s'",
                expectedType.toString()), this.getLocation()); // Rule 3.28
    }


    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        Validate.notNull(localEnv);

        this.verifyExpr(compiler, localEnv, currentClass);
    }

    /**
     * Verify the expression as a condition, i.e. check that the type is
     * boolean.
     *
     * @param localEnv
     *                     Environment in which the condition should be checked.
     * @param currentClass
     *                     Definition of the class containing the expression, or
     *                     null in
     *                     the main program.
     */
    void verifyCondition(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        if (!this.verifyExpr(compiler, localEnv, currentClass).isBoolean()) {
            throw new ContextualError("La condition ne renvoie pas un boolean", this.getLocation()); // Rule 3.29
        }
    }

    /**
     * Generate code to print the expression
     *
     * @param compiler
     */
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex, String name) {
        int nActual = compiler.getN() + 1;
        this.codeGenInst(compiler, name);
        if (this.getType().isInt()) {
            compiler.addInstruction(SuperLOAD.main(Register.getR(nActual), Register.R1, compiler.compileInArm()));
            compiler.addInstruction(SuperWINT.main(compiler.compileInArm()));
        }
        if (this.getType().isFloat()) {
            compiler.addInstruction(SuperLOAD.main(Register.getR(nActual), Register.R1, compiler.compileInArm()));
            if (!printHex) {
                compiler.addInstruction(SuperWFLOAT.main(compiler.compileInArm()));
            } else {
                compiler.addInstruction(SuperWFLOATX.main(compiler.compileInArm()));
            }
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
        compiler.setN(compiler.getN() + 1);
        BlocInProg.getBlock(name).incrnbRegisterNeeded(compiler.getN());
        if (this.getType().sameType(compiler.environmentType.INT)) {
            IntLiteral intExpr = (IntLiteral) this;
            compiler.addInstruction(SuperLOAD.main(new ImmediateInteger(intExpr.getValue()),
                    Register.getR(compiler.getN()), compiler.compileInArm()));
        }
        if (this.getType().sameType(compiler.environmentType.FLOAT)) {
            FloatLiteral intExpr = (FloatLiteral) this;
            compiler.addInstruction(SuperLOAD.main(new ImmediateFloat(intExpr.getValue()),
                    Register.getR(compiler.getN()), compiler.compileInArm()));
        }
        if (this.getType().sameType(compiler.environmentType.BOOLEAN)) {
            BooleanLiteral intExpr = (BooleanLiteral) this;
            if (intExpr.getValue()) {
                compiler.addInstruction(SuperLOAD.main(new ImmediateInteger(1), Register.getR(compiler.getN()),
                        compiler.compileInArm()));
            } else {
                compiler.addInstruction(SuperLOAD.main(new ImmediateInteger(0), Register.getR(compiler.getN()),
                        compiler.compileInArm()));
            }
        }
    }

    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }

    @Override
    protected void checkDecoration() {
        LOG.debug(this.getLocation().toString());
        Validate.notNull(this.getType());
    }
}
