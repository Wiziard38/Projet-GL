package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.TernaryInstruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SUBSP extends TernaryInstruction {

    public SUBSP(ImmediateInteger operand) {
        super(fr.ensimag.pseudocode.Register.SP, operand, fr.ensimag.pseudocode.Register.SP);
    }

    public SUBSP(int i) {
        this(new ImmediateInteger(i));
    }

}
