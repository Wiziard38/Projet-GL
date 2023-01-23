package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 * BLE instruction pour arm
 * 
 * @author gl39
 */
public class BLE extends BranchInstruction {

    public BLE(Label op) {
        super(op);
    }

}
