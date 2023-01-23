package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.TernaryInstructionDValToReg;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;

/**
 * DIV instruction pour arm
 * 
 * @author gl39
 */
public class SDIV extends TernaryInstructionDValToReg {

    public SDIV(DVal op1, GPRegister op2) {
        super(op1, op2);
    }

}
