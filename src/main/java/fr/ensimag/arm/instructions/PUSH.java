package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.UnaryInstruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class PUSH extends UnaryInstruction {
    public PUSH(Register op1) {
        super(op1);
    }
}
