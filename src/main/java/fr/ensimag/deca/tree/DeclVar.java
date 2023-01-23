package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.BlocInProg;
import fr.ensimag.deca.codegen.VariableAddr;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.superInstructions.SuperPUSH;

/**
 * @author gl39
 * @date 01/01/2023
 */
public class DeclVar extends AbstractDeclVar {
    private static final Logger LOG = Logger.getLogger(DeclVar.class);

    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {

        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    protected void codeGenVarMeth(DecacCompiler compiler, String nameBloc, int pos) {

        compiler.addComment(this.decompile());
        BlocInProg.getBlock(nameBloc).incrnbPlacePileNeeded();
        int spActual = compiler.getSP() + 1;
        compiler.setSP(spActual);
        int nAct = compiler.getN() + 1;
        initialization.codeGenInst(compiler, varName.getDefinition(), nameBloc);
        compiler.addInstruction(SuperPUSH.main(Register.getR(nAct), compiler.compileInArm()));
        VariableDefinition varDef = (VariableDefinition) varName.getDefinition();
        varDef.setOperand(new RegisterOffset(pos, Register.SP));
        compiler.setN(nAct - 1);
        compiler.addComment("");
    }


    protected void codeGenVar(DecacCompiler compiler, String name) {

        compiler.addComment(this.decompile());
        BlocInProg.getBlock(name).incrnbPlacePileNeeded();
        int spActual = compiler.getSP() + 1;
        int nAct = compiler.getN() + 1;
        initialization.codeGenInst(compiler, varName.getDefinition(), name);
        compiler.setSP(spActual);
        compiler.addInstruction(SuperPUSH.main(Register.getR(nAct), compiler.compileInArm()));
        VariableDefinition varDef = (VariableDefinition) varName.getDefinition();
        varDef.setOperand(new RegisterOffset(spActual, Register.GB));
        compiler.setN(nAct - 1);
        compiler.addComment("");
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Validate.notNull(localEnv);

        // LOG.debug("Verify Decl Var - type");
        // On verifie que le type existe bien
        Type initializationType = this.type.verifyType(compiler, true, "une variable");
        // this.type.setDefinition(compiler.environmentType.defOfType(this.type.getName()));

        // LOG.debug("Verify Decl Var - name");
        // On verifie que varName n'est pas deja declare localement
        try {
            this.varName.setDefinition(new VariableDefinition(initializationType, this.getLocation()));
            localEnv.declare(this.varName.getName(), this.varName.getExpDefinition());
        } catch (Exception DoubleDefException) {
            throw new ContextualError(String.format("Le nom de variable '%s' est deja déclaré dans l'environnement",
                    this.varName.getName()), this.getLocation()); // Rule 3.17
        }

        // LOG.debug("Verify Decl Var - initialization");
        this.initialization.verifyInitialization(compiler, initializationType, localEnv, currentClass);

    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
        s.print(";");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
