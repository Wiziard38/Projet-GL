package fr.ensimag.ima.instructions;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.UnaryInstructionToReg;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class POP extends UnaryInstructionToReg {

    public POP(GPRegister op) {
        super(op);
    }

}
