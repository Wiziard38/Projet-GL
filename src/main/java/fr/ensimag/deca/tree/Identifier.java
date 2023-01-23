package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperLEA;
import fr.ensimag.superInstructions.SuperLOAD;
import fr.ensimag.superInstructions.SuperOffset;
import fr.ensimag.superInstructions.SuperWFLOAT;
import fr.ensimag.superInstructions.SuperWFLOATX;
import fr.ensimag.superInstructions.SuperWINT;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca Identifier
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Identifier extends AbstractIdentifier {
    private static final Logger LOG = Logger.getLogger(Identifier.class);

    /**
     * Genère le code pour print un identifier.
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param printHex bollean qui dit si il faut print en héxadécimale
     * @param name     le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex, String name) {
        int nActual = compiler.getN() + 1;
        this.codeGenInst(compiler, name);
        compiler.addInstruction(SuperLOAD.main(Register.getR(nActual), Register.R1, compiler.compileInArm()));
        if (this.getType().isInt()) {
            compiler.addInstruction(SuperWINT.main(compiler.compileInArm()));
        } else {
            if (!printHex) {
                compiler.addInstruction(SuperWFLOAT.main(compiler.compileInArm()));
            } else {
                compiler.addInstruction(SuperWFLOATX.main(compiler.compileInArm()));
            }

        }
    }

    /**
     * Genère le code d'un identifier hors d'un print
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param nameBloc le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler, String nameBloc) {
        LOG.debug("nom du bloc: " + nameBloc);
        int nActual = compiler.getN() + 1;
        compiler.setN(nActual);
        BlocInProg.getBlock(nameBloc).incrnbRegisterNeeded(compiler.getN());
        switch (this.getDefinition().getNature()) {
            case "variable":
                VariableDefinition defVar = (VariableDefinition) this.getDefinition();
                compiler.addInstruction(
                        SuperLOAD.main(defVar.getOperand(), Register.getR(nActual), compiler.compileInArm()));
                break;
            case "field":
                FieldDefinition defField = (FieldDefinition) this.getDefinition();
                compiler.addInstruction(SuperLOAD.main(SuperOffset.main(-2, Register.LB, compiler.compileInArm()),
                        Register.getR(nActual), compiler.compileInArm()));
                compiler.addInstruction(SuperLOAD.main(
                        SuperOffset.main(defField.getIndex(), Register.getR(nActual), compiler.compileInArm()),
                        Register.getR(nActual), compiler.compileInArm()));
                break;
            case "parameter":
                ParamDefinition defParam = (ParamDefinition) this.getDefinition();
                compiler.addInstruction(
                        SuperLOAD.main(SuperOffset.main(-defParam.getIndex() - 2, Register.LB, compiler.compileInArm()),
                                Register.getR(nActual), compiler.compileInArm()));
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *                            if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *                            if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *                            if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *                            if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *                            if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Validate.notNull(localEnv);

        if (localEnv.get(this.name) == null) {
            throw new ContextualError(String.format("Identificateur '%s' non déclaré dans l'environnement",
                    this.name.getName()), this.getLocation()); // Rule 0.1
        }

        this.setDefinition(localEnv.get(this.name));
        return localEnv.get(this.name).getType();
    }

    @Override
    public Definition verifyDefinition(DecacCompiler compiler, EnvironmentExp localEnv)
            throws ContextualError {
        Validate.notNull(localEnv);

        if (localEnv.get(this.name) == null) {
            throw new ContextualError(String.format("Identificateur '%s' non déclaré dans l'environnement",
                    this.name.getName()), this.getLocation()); // Rule 0.1
        }

        this.setDefinition(localEnv.get(this.name));
        return localEnv.get(this.name);
    }

    @Override
    public Type verifyType(DecacCompiler compiler, boolean checkVoid, String message) throws ContextualError {
        TypeDefinition thisTypeDef = compiler.environmentType.defOfType(this.getName());
        if (thisTypeDef == null) {
            throw new ContextualError(String.format("Identificateur de type '%s' non déclaré",
                    this.name.getName()), this.getLocation()); // Rule 0.2
        }
        if (checkVoid && thisTypeDef.getType().isVoid()) {
            throw new ContextualError(String.format("Le type void ne peut etre affecté pour %s",
                    message), this.getLocation()); // Rule 2.5 // Rule 2.9 // Rule 3.17
        }

        this.setDefinition(compiler.environmentType.defOfType(this.getName()));
        return thisTypeDef.getType();
    }

    @Override
    public Type verifyLValue(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        LOG.debug(this.getName());

        Type requestedType = this.verifyExpr(compiler, localEnv, currentClass);

        if (!localEnv.get(this.getName()).isField() && !localEnv.get(this.getName()).isParam()
                && !localEnv.get(this.getName()).isExpression()) {
            LOG.debug(localEnv.get(this.getName()).isExpression());
            throw new ContextualError("La valeur de gauche doit être une variable, un paramètre ou un champ",
                    this.getLocation()); // Rule 3.67 // Rule 3.68 // Rule 3.69
        }
        return requestedType;
    }

    private Definition definition;

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

    @Override
    public Type getType() {
        return this.getDefinition().getType();
    }

    @Override
    public String toString() {
        return this.getName().toString();
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
        compiler.setN(compiler.getN() + 1);
        compiler.addInstruction(SuperLEA.main(this.getExpDefinition().getOperand(), Register.getR(compiler.getN() + 1),
                compiler.compileInArm()));

    }
}
