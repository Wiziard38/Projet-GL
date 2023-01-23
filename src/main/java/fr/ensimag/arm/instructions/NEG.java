package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;

/**
 * OPP instruction pour arm
 * 
 * @author gl39
 */
public class NEG extends BinaryInstructionDValToReg {
    public NEG(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
}
