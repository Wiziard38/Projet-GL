package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

/**
 *
 * @author gl39
 * @date 01/01/2023
 */
public abstract class AbstractOpIneq extends AbstractOpCmp {

    public AbstractOpIneq(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void checkDecoration() {
        Validate.isTrue(this.getLeftOperand().getType().isInt() || this.getRightOperand().getType().isFloat());
        Validate.isTrue(this.getLeftOperand().getType().sameType(this.getRightOperand().getType()));
        Validate.isTrue(this.getType().isBoolean());
    }

}
