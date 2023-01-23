package fr.ensimag.pseudocode;

/**
 * Base class for instructions with 2 operands, the first being a
 * DVal, and the second a Register except it is for arm
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class BinaryInstructionDValToRegArm extends BinaryInstruction {
    public BinaryInstructionDValToRegArm(DVal op1, GPRegister op2) {
        super(op2, op1);
    }
}
