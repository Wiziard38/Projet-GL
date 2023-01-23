package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstructionDValToRegArm;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ImmediateInteger;

/**
 * LOAD instruction pour arm
 * 
 * @author gl39
 */
public class LDR extends BinaryInstructionDValToRegArm {

    public LDR(DVal op1, GPRegister op2) {
        super(op1, op2);
    }

    public LDR(int i, GPRegister r) {
        this(new ImmediateInteger(i), r);
    }

}
