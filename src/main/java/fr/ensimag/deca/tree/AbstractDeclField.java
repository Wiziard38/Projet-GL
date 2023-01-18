package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

public abstract class AbstractDeclField extends Tree {

        /**
         * TODO
         */
        public abstract void verifyEnvField(DecacCompiler compiler, ClassDefinition currentClassDef,
                        AbstractIdentifier superClass) throws ContextualError;

        /**
         * TODO
         */
        public abstract void verifyInitField(DecacCompiler compiler, ClassDefinition currentClassDef)
                        throws ContextualError;
        protected abstract void codeGenDeclFiedl(DecacCompiler compiler, String name);
        public abstract AbstractIdentifier getName();
}
