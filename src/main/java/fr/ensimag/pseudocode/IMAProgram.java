package fr.ensimag.pseudocode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

/**
 * Abstract representation of an IMA program, i.e. set of Lines.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class IMAProgram {
    private final LinkedList<AbstractLine> lines = new LinkedList<AbstractLine>();
    private Label erreurPile = new Label("ErreurPile");
    private boolean ln = false;

    public Label getErreurPile() {
        return erreurPile;
    }

    private Label erreurOverflow = new Label("overflow_error");

    public Label getErreurOverflow() {
        return erreurOverflow;
    }

    private Label erreurInOut = new Label("io_error");

    public Label getErreurinOut() {
        return erreurInOut;
    }

    public void addIndex(Instruction inst, int i) {
        lines.add(i, new Line(inst));
    }

    public int getLastLineIndex() {
        return lines.size();
    }

    public void add(AbstractLine line) {
        lines.add(line);
    }

    public void addComment(String s) {
        lines.add(new Line(s));
    }

    public void addLabel(Label l) {
        lines.add(new Line(l));
    }

    public void addInstruction(Instruction i) {
        lines.add(new Line(i));
    }

    public void addInstruction(Instruction i, String s) {
        lines.add(new Line(null, i, s));
    }

    /**
     * Append the content of program p to the current program. The new program
     * and p may or may not share content with this program, so p should not be
     * used anymore after calling this function.
     */
    public void append(IMAProgram p) {
        lines.addAll(p.lines);
    }

    /**
     * Add a line at the front of the program.
     */
    public void addFirst(Line l) {
        lines.addFirst(l);
    }

    public IMAProgram(boolean arm) {
        this.arm = arm;
    }

    private boolean arm;

    /**
     * Display the program in a textual form readable by IMA to stream s.
     */
    public void display(PrintStream s) {
        if (arm) {
            // on écrit les données nécessaires à arm pour faire des retours à la ligne
            s.println(".data");
            s.println();
            s.println("msg_retourLigne:");
            s.println(".asciz \"\\n\"");
            s.println("lenretourLigne = . - msg_retourLigne");
            s.print("\n");
        }
        for (AbstractLine l : lines) {
            l.display(s);
        }
    }

    /**
     * Return the program in a textual form readable by IMA as a String.
     */
    public String display() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream s = new PrintStream(out);
        display(s);
        return out.toString();
    }

    public void addFirst(Instruction i) {
        addFirst(new Line(i));
    }

    public void addFirst(Instruction i, String comment) {
        addFirst(new Line(null, i, comment));
    }

    /**
     * Sert à savoir s'il faut écrire les données utiles pour faire des retours à la
     * ligne.
     *
     * @author gl39
     * @date 01/01/2023
     */
    public void writePrintLabel() {
        ln = true;
    }
}
