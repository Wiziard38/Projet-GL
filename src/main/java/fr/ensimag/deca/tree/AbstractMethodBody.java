package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public abstract class AbstractMethodBody extends Tree {


    /**
     * TODO
     * @param compiler
     * @param paramsEnvExp
     * @param currentClassDef
     * @param returnType
     * @throws ContextualError
     */
    public abstract void verifyBody(DecacCompiler compiler, EnvironmentExp paramsEnvExp,
            ClassDefinition currentClassDef, Type returnType) throws ContextualError;

    protected abstract void codeGenInstBody(DecacCompiler compiler);
}
