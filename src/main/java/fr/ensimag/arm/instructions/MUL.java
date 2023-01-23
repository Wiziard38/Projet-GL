package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.TernaryInstructionDValToReg;

/**
 * MUL instruction pour arm
 * 
 * @author gl39
 */
public class MUL extends TernaryInstructionDValToReg {
    public MUL(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
}
