package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;

/**
 * Test d'égalité déca
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "==";
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }

}
