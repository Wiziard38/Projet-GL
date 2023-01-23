package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl39
 * @date 01/01/2023
 */
public class ListDeclVar extends TreeList<AbstractDeclVar> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclVar c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Genère le code d'une liste de varaible locale à une méthode
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param nameBloc le nom du bloc ou on gènere le code assembleur
     */

    public void codeGenListVarMeth(DecacCompiler compiler, String name){
        int j = 1;
        for (AbstractDeclVar i : getList()) {
            i.codeGenVarMeth(compiler, name, j - this.getList().size());
            j += 1;
        }
    }

    /**
     * Genère le code d'une liste de variables globales
     *
     * @param compiler compilateur ou ajouter les instructions
     * @param nameBloc le nom du bloc ou on gènere le code assembleur
     */
    public void codeGenListVar(DecacCompiler compiler, String nameBloc) {
        for (AbstractDeclVar i : getList()) {
            i.codeGenVar(compiler, nameBloc);
        }
    }

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     * 
     * @param compiler     contains the "env_types" attribute
     * @param localEnv
     *                     its "parentEnvironment" corresponds to "env_exp_sup"
     *                     attribute
     *                     in precondition, its "current" dictionary corresponds to
     *                     the "env_exp" attribute
     *                     in postcondition, its "current" dictionary corresponds to
     *                     the "env_exp_r" attribute
     * @param currentClass
     *                     corresponds to "class" attribute (null in the main bloc).
     */
    void verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Validate.notNull(localEnv);

        for (AbstractDeclVar currentDeclaration : this.getList()) {
            currentDeclaration.verifyDeclVar(compiler, localEnv, currentClass);
        }

    }

}
