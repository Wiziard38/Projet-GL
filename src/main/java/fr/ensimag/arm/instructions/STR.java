package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Register;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class STR extends BinaryInstruction {
    public STR(Register op1, DAddr op2) {
        super(op1, op2);
    }
}

// STORE