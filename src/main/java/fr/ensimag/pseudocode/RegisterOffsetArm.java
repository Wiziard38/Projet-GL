package fr.ensimag.pseudocode;

public class RegisterOffsetArm extends RegisterOffset {

    public RegisterOffsetArm(int offset, Register register) {
        super(offset, register);
    }

    @Override
    public String toString() {
        return "[" + getRegister() + ", #" + -getOffset() + "]";
    }

}
