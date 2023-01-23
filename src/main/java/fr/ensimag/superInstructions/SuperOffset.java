package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Register;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.pseudocode.RegisterOffsetArm;

/**
 * Class used to send an Offset depending on wether we compile in ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperOffset {

    public static DAddr main(int offset, Register register, boolean arm) {
        if (arm) {
            register = register.convertToArmRegister();
            return new RegisterOffsetArm(offset, register);
        }
        return new RegisterOffset(offset, register);
    }

}
