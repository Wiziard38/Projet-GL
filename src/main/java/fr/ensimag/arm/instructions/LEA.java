package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstructionDAddrToReg;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.GPRegister;

/**
 * LEA instruction pour arm
 * 
 * @author gl39
 */
public class LEA extends BinaryInstructionDAddrToReg {

    public LEA(DAddr op1, GPRegister op2) {
        super(op1, op2);
    }

}
