package fr.ensimag.pseudocode;

/**
 * Base class for instructions with 2 operands, the first being a
 * DVal, and the second a Register.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class BinaryInstructionRegToDVal extends BinaryInstruction {
    public BinaryInstructionRegToDVal(GPRegister op1, DVal op2) {
        super(op1, op2);
    }
}
