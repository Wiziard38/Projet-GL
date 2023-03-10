package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.superInstructions.SuperWSTR;
import java.io.PrintStream;
import org.apache.log4j.Logger;

import org.apache.commons.lang.Validate;

/**
 * String literal
 *
 * @author gl39
 * @date 01/01/2023
 */
public class StringLiteral extends AbstractStringLiteral {
    private static final Logger LOG = Logger.getLogger(StringLiteral.class);

    @Override
    public String getValue() {
        return value;
    }

    private String value;

    public StringLiteral(String value) {
        Validate.notNull(value);
        this.value = value.replaceAll("\"", "");
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        LOG.debug("verify getting into string literal");
        this.setType(compiler.environmentType.STRING);
        return compiler.environmentType.STRING;
    }

    /**
     * Genère le code d'un print de StringLiteral
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param name     le nom du bloc ou on gènere le code assembleur
     */
    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex, String name) {
        compiler.addInstruction(SuperWSTR.main(this.value, compiler.compileInArm()));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("\"");
        s.print(value);
        s.print("\"");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return String.format("StringLiteral (%s)", this.value);
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }

}
