package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class BEQ extends BranchInstruction {

    public BEQ(Label op) {
        super(op);
    }

}
