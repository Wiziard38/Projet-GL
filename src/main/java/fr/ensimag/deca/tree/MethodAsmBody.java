package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Line;

/**
 * Method body declaration pour assembleur
 *
 * @author gl39
 * @date 01/01/2023
 */
public class MethodAsmBody extends AbstractMethodBody {
    private static final Logger LOG = Logger.getLogger(ClassDefinition.class);
    private String textAsm;
    private Location location;

    public MethodAsmBody(String txt, Location loc) {
        Validate.notNull(loc);
        Validate.notNull(txt);
        textAsm = txt;
        location = loc;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(" asm(");
        s.indent();
        s.print(textAsm);
        s.unindent();
        s.print(");");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        s.println(prefix + "+> " + location + " Assembler (" + textAsm + ")");

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // nothing to do here
    }

    @Override
    public void verifyBody(DecacCompiler compiler, EnvironmentExp paramsEnvExp,
            ClassDefinition currentClassDef, Type returnType) throws ContextualError {
        // nothing to do here
    }

    /**
     * Genère le code d'une méthode écrite en assembleur, un constructeur de Line a
     * été rajouté pour cela.
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param nameBloc le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenInstBody(DecacCompiler compiler, String name) {
        compiler.add(new Line(textAsm.replaceAll("\"", ""), true));
    }

}
