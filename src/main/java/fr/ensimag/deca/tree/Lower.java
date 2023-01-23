package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;

/**
 * Inférieur strict operation
 *
 * @author gl39
 * @date 01/01/2023
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "<";
    }

    @Override
    public void codeGenVarAddr(DecacCompiler compiler, String nameBloc) {
    }
}
