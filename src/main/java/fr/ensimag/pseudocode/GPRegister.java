package fr.ensimag.pseudocode;

/**
 * General Purpose Register operand (R0, R1, ... R15).
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class GPRegister extends Register {
    /**
     * @return the number of the register, e.g. 12 for R12.
     */
    public int getNumber() {
        return number;
    }

    private int number;

    GPRegister(String name, int number) {
        super(name);
        this.number = number;
    }

    @Override
    public GPRegister convertToArmRegister() {
        if (this == GB) {
            return getR(11);
        } else if (this == LB) {
            return getR(10);
        } else
            return this;
    }
}
