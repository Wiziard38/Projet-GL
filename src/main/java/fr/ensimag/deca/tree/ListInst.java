package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * List of instruction
 * 
 * @author gl39
 * @date 01/01/2023
 */
public class ListInst extends TreeList<AbstractInst> {
    private static final Logger LOG = Logger.getLogger(ListInst.class);

    /**
     * Implements non-terminal "list_inst" of [SyntaxeContextuelle] in pass 3
     * 
     * @param compiler     contains "env_types" attribute
     * @param localEnv     corresponds to "env_exp" attribute
     * @param currentClass
     *                     corresponds to "class" attribute (null in the main bloc).
     * @param returnType
     *                     corresponds to "return" attribute (void in the main
     *                     bloc).
     */
    public void verifyListInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        // LOG.debug("verify into listInst");
        Validate.notNull(localEnv);

        for (AbstractInst myInst : getList()) {
            myInst.verifyInst(compiler, localEnv, currentClass, returnType);
        }
    }


    /**
     * Genère le code d'une liste d'instructions
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param name le nom du bloc ou on gènere le code assembleur
     */
    public void codeGenListInst(DecacCompiler compiler, String name) {
        for (AbstractInst i : getList()) {
            //compiler.addComment(i.decompile());;
            i.codeGenInst(compiler, name);
            compiler.addComment("");
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractInst i : getList()) {
            i.decompileInst(s);
            s.println();
        }
    }
}
