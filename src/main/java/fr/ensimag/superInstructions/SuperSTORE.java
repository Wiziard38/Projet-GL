package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Instruction;
import fr.ensimag.pseudocode.Register;


/**
 * Class used to send the STORE instruction depending on wether we compile in ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperSTORE {

    public static Instruction main(Register op1, DAddr op2, boolean arm) {
        if (arm) {
            op1 = op1.convertToArmRegister();
            return new fr.ensimag.arm.instructions.STR(op1, op2);
        } else {
            return new fr.ensimag.ima.instructions.STORE(op1, op2);
        }
    }
}