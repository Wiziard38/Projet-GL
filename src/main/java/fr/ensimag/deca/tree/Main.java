package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.superInstructions.SuperBOV;
import fr.ensimag.superInstructions.SuperERROR;
import fr.ensimag.superInstructions.SuperHALT;
import fr.ensimag.superInstructions.SuperTSTO;
import fr.ensimag.superInstructions.SuperWNL;
import fr.ensimag.superInstructions.SuperWSTR;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl39
 * @date 01/01/2023
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);

    private ListDeclVar declVariables;
    private ListInst insts;

    public Main(ListDeclVar declVariables, ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify Main: start");

        EnvironmentExp localEnv = new EnvironmentExp(null);
        this.declVariables.verifyListDeclVariable(compiler, localEnv, null);

        LOG.debug(String.format("symbol : %s", compiler.symbolTable.getTable().get("x")));
        LOG.debug(String.format("env : %s", localEnv.getLocalEnv().toString()));

        this.insts.verifyListInst(compiler, localEnv, null, null);
        LOG.debug("verify Main: end");
    }

    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        // A FAIRE: traiter les déclarations de variables.
        this.declVariables.codeGenListVar(compiler);
        compiler.addComment("Beginning of main instructions:");
        insts.codeGenListInst(compiler);
        compiler.addInstruction(SuperHALT.main(compiler.compileInArm()));
        compiler.addComment("End of the main program");
        compiler.addLabel(compiler.getErreurPile());
        compiler.addInstruction(
                SuperWSTR.main("Erreur de débordement de pile dans le programme", compiler.compileInArm()));
        compiler.addInstruction(SuperWNL.main(compiler.compileInArm()));
        compiler.addInstruction(SuperERROR.main(compiler.compileInArm()));
        compiler.addLabel(compiler.getErreurOverflow());
        compiler.addInstruction(
                SuperWSTR.main("Erreur 'overflow' pendant une opération arithmétique", compiler.compileInArm()));
        compiler.addInstruction(SuperWNL.main(compiler.compileInArm()));
        compiler.addInstruction(SuperERROR.main(compiler.compileInArm()));
        compiler.addLabel(compiler.getErreurinOut());
        compiler.addInstruction(SuperWSTR.main("Erreur lors d'une entrée/sortie", compiler.compileInArm()));
        compiler.addInstruction(SuperWNL.main(compiler.compileInArm()));
        compiler.addInstruction(SuperERROR.main(compiler.compileInArm()));
        compiler.addLabel(compiler.getErreurArrondi());
        compiler.addInstruction(
                SuperWSTR.main("Erreur lors d'une opération arithmétique sur des flottant, arrondi vers 0 ou l'infini",
                        compiler.compileInArm()));
        compiler.addInstruction(SuperWNL.main(compiler.compileInArm()));
        compiler.addInstruction(SuperERROR.main(compiler.compileInArm()));
        compiler.addInstructionFirst(SuperBOV.main(compiler.getErreurPile(), compiler.compileInArm()));
        compiler.addInstructionFirst(SuperTSTO.main(compiler.getD(), compiler.compileInArm()));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
