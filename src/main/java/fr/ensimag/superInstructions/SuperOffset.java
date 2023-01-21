package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.pseudocode.RegisterOffsetArm;

public class SuperOffset {

    public Operand main(int offset, Register register, boolean arm) {
        if (arm) {
            return new RegisterOffsetArm(offset, register);
        }
        return new RegisterOffset(offset, register);
    }

}
