package fr.ensimag.pseudocode;

public class RegisterOffsetArm extends RegisterOffset {

    public RegisterOffsetArm(int offset, Register register) {
        super(offset, register);
        System.out.println("2" + offset);
    }

    @Override
    public String toString() {
        System.out.println("3" + getOffset());
        return "[" + getRegister() + ", #" + -getOffset() * 4 + "]";
    }

}
