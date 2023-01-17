package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;

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
}
