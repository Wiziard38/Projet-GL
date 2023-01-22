package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ExpDefinition;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
    
    @Override
    protected String getOperatorName() {
        return "!=";
    }
}
