package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.superInstructions.SuperWNL;

/**
 * @author gl39
 * @date 01/01/2023
 */
public class Println extends AbstractPrint {

    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex  if true, then float should be displayed as hexadecimal
     *                  (printlnx)
     */
    public Println(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    /**
     * Genère le code pour un print ln, on génére le code pour un print et on, print un \n
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param name le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
        super.codeGenInst(compiler, name);
        compiler.addInstruction(SuperWNL.main(compiler.compileInArm()));
    }

    @Override
    String getSuffix() {
        return "ln";
    }
}
