package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ExpDefinition;

/**
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
}
