package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.TernaryInstruction;

/**
 * Add a value to stack pointer.
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class ADDSP extends TernaryInstruction {

    public ADDSP(ImmediateInteger operand) {
        super(fr.ensimag.pseudocode.Register.SP, operand, fr.ensimag.pseudocode.Register.SP);
    }

    public ADDSP(int i) {
        this(new ImmediateInteger(i));
    }

}
