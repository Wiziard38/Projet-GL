package fr.ensimag.pseudocode;

/**
 * Base class for instructions with 2 operands, the first being a
 * DVal, and the second a Register.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class TernaryInstructionDValToReg extends TernaryInstruction {
    public TernaryInstructionDValToReg(DVal op1, Register op2) {
        super(op2, op1, op2);
    }
}
