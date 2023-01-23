package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.pseudocode.RegisterOffsetArm;

public class SuperOffset {

    public static DAddr main(int offset, Register register, boolean arm) {
        System.out.println("1" + offset);
        if (arm) {
            register = register.convertToArmRegister();
            return new RegisterOffsetArm(offset, register);
        }
        return new RegisterOffset(offset, register);
    }

}
