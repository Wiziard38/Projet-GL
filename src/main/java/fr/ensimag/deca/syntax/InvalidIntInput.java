package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Exception raised when a Float value is declared too big or too low
 *
 * @author gl39
 * @date 01/01/2023
 */
public class InvalidIntInput extends DecaRecognitionException {

    public InvalidIntInput(DecaParser recognizer, ParserRuleContext ctx) {
        super(recognizer, ctx);
    }

    @Override
    public String getMessage() {
        return "Illegal Int value: too big";
    }

    private static final long serialVersionUID = 2L;
}
