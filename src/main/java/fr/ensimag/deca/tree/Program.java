package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.superInstructions.SuperERROR;
import fr.ensimag.superInstructions.SuperHALT;
import fr.ensimag.superInstructions.SuperWNL;
import fr.ensimag.superInstructions.SuperWSTR;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);

    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }

    public ListDeclClass getClasses() {
        return classes;
    }

    public AbstractMain getMain() {
        return main;
    }

    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify program: start");
        // Passe 1 :
        this.classes.verifyListClass(compiler);
        // Passe 2 :
        this.classes.verifyListClassMembers(compiler);
        // Passe 3 :
        this.classes.verifyListClassBody(compiler);
        this.main.verifyMain(compiler);
        LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        // if (!compiler.compileInArm()) {
        // Passe numéro 1 des classes déclaration des classes et méthodes
        compiler.addComment("Class declaration");
        classes.codeGenListClass(compiler);
        // }

        // Passe sur le programme principal pour la génération de son code
        compiler.addComment("Main Program");
        main.codeGenMain(compiler);
        compiler.addInstruction(SuperHALT.main(compiler.compileInArm()));
        compiler.addComment("");
        // if (!compiler.compileInArm()) {
        compiler.addComment("Method declaration");
        classes.codeGenCorpMethod(compiler, "");
        compiler.addLabel(compiler.getErreurPile());
        compiler.addInstruction(
                SuperWSTR.main("Erreur de débordement de pile dans le programme", compiler.compileInArm()));
        compiler.addInstruction(SuperWNL.main(compiler.compileInArm()));
        compiler.addInstruction(SuperERROR.main(compiler.compileInArm()));
        compiler.addComment("");
        compiler.addLabel(compiler.getErreurOverflow());
        compiler.addInstruction(
                SuperWSTR.main("Erreur 'overflow' pendant une opération arithmétique", compiler.compileInArm()));
        compiler.addInstruction(SuperWNL.main(compiler.compileInArm()));
        compiler.addInstruction(SuperERROR.main(compiler.compileInArm()));
        compiler.addComment("");
        compiler.addLabel(compiler.getErreurinOut());
        compiler.addInstruction(SuperWSTR.main("Erreur lors d'une entrée/sortie", compiler.compileInArm()));
        compiler.addInstruction(SuperWNL.main(compiler.compileInArm()));
        compiler.addInstruction(SuperERROR.main(compiler.compileInArm()));
        compiler.addComment("");
        compiler.addLabel(compiler.getErreurArrondi());
        compiler.addInstruction(
                SuperWSTR.main(
                        "Erreur lors d'une opération arithmétique sur des flottant, arrondi vers 0 ou l'infini",
                        compiler.compileInArm()));
        compiler.addInstruction(SuperWNL.main(compiler.compileInArm()));
        compiler.addInstruction(SuperERROR.main(compiler.compileInArm()));
        compiler.addComment("");
        // Gestion de la pile
        // compiler.addInstructionFirst(SuperBOV.main(compiler.getErreurPile(),
        // compiler.compileInArm()));
        // compiler.addInstructionFirst(SuperTSTO.main(compiler.getD(),
        // compiler.compileInArm()));
        // }
        // Passe numéro 2 des classes, on code le corp des méthodes

    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
