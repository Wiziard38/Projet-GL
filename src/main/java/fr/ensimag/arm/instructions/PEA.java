package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.UnaryInstruction;

/**
 * PEA instruction pour arm
 * 
 * @author gl39
 */
public class PEA extends UnaryInstruction {

    public PEA(DAddr operand) {
        super(operand);
    }

}
