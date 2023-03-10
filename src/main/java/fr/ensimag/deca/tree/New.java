package fr.ensimag.deca.tree;

import java.io.PrintStream;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.superInstructions.SuperBSR;
import fr.ensimag.superInstructions.SuperLEA;
import fr.ensimag.superInstructions.SuperNEW;
import fr.ensimag.superInstructions.SuperOffset;
import fr.ensimag.superInstructions.SuperPOP;
import fr.ensimag.superInstructions.SuperPUSH;
import fr.ensimag.superInstructions.SuperSTORE;

/**
 * Appel du constructeur pour instancier un objet.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class New extends AbstractExpr {

    private AbstractIdentifier name;

    public New(AbstractIdentifier name) {
        this.name = name;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        this.name.decompile(s);
        s.print("()");
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        TypeDefinition exprType = compiler.environmentType.defOfType(this.name.getName());
        if (exprType == null || !exprType.isClass()) {
            throw new ContextualError("'New' ne peut etre affecté que pour une class",
                    this.getLocation()); // Rule 3.42
        }

        this.setType(exprType.getType());
        this.name.setDefinition(compiler.environmentType.getClass(this.name.getName()));

        return exprType.getType();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.name.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.name.iter(f);
    }

    /**
     * Genère le code d'un new
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param nameBloc le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler, String nameBloc) {
        int nActual = compiler.getN() + 1;
        compiler.setN(nActual);
        BlocInProg.getBlock(nameBloc).incrnbRegisterNeeded(compiler.getN());
        compiler.addInstruction(
                SuperNEW.main(compiler.environmentType.getClass(this.name.getName()).getNumberOfFields() + 1,
                        Register.getR(nActual), compiler.compileInArm()));
        int nAdrr = compiler.getN() + 1;
        compiler.setN(nAdrr);
        compiler.addInstruction(SuperLEA.main(compiler.environmentType.getClass(this.name.getName()).getOperand(),
                Register.getR(nAdrr), compiler.compileInArm()));
        compiler.addInstruction(SuperSTORE.main(Register.getR(nAdrr),
                SuperOffset.main(0, Register.getR(nActual), compiler.compileInArm()), compiler.compileInArm()));
        compiler.addInstruction(SuperPUSH.main(Register.getR(nActual), compiler.compileInArm()));
        compiler.setN(nActual);
        compiler.addInstruction(SuperBSR.main(
                new Label(
                        "init." + this.name.getName().getName() + this.name.getClassDefinition().getLocation().getLine()
                                + this.name.getClassDefinition().getLocation().getPositionInLine()),
                compiler.compileInArm()));
        compiler.addInstruction(SuperPOP.main(Register.getR(nActual), compiler.compileInArm()));
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }
}
