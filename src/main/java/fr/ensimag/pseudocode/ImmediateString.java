package fr.ensimag.pseudocode;

/**
 * Immediate operand representing a string.
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class ImmediateString extends Operand {
    private String value;

    public ImmediateString(String value) {
        super();
        this.value = value;
    }

    public String stripped() {
        if (value == "\n") {
            return "retourLigne";
        }
        return value.replaceAll("[^a-zA-Z0-9]", "");
    }

    @Override
    public String toString() {
        if (value == "\n") {
            return "\"\\n\"";
        }
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
