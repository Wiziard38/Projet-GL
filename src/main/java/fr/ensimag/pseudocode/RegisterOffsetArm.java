package fr.ensimag.pseudocode;

/**
 * Register offset except it is for arm instruction.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class RegisterOffsetArm extends RegisterOffset {

    public RegisterOffsetArm(int offset, Register register) {
        super(offset, register);
    }

    @Override
    public String toString() {
        return "[" + getRegister() + ", #" + -getOffset() * 4 + "]";
    }

}
