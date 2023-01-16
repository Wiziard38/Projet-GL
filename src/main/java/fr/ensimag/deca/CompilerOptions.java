package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;

    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }

    public boolean getVerification() {
        return verification;
    }

    public boolean getCompileInARM() {
        return compileInARM;
    }

    public boolean getParsing() {
        return parsing;
    }

    public boolean getCheck() {
        return check;
    }

    public int getnumberRegisters() {
        return numberRegisters;
    }

    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private boolean parallel = false;
    private boolean compileInARM = false;
    private boolean parsing = false;
    private boolean verification = false;
    private boolean check = false;
    private boolean printBanner = false;
    private int numberRegisters = 16;
    private List<File> sourceFiles = new ArrayList<File>();

    public void parseArgs(String[] args) throws CLIException {

        int index = 0;
        while (index < args.length) {

            String arg = args[index];
            switch (arg) {

                case "-b": // banner
                    this.printBanner = true;
                    if (args.length != 1) {
                        throw new CLIException("Option '-b' must be used alone");
                    }

                case "-p": // parse
                    this.parsing = true;
                    if (this.verification || this.compileInARM) {
                        throw new CLIException("Cannot use options '-p' and '-v' and '-arm' together");
                    }
                    break;

                case "-v": // verification
                    this.verification = true;
                    if (this.parsing || this.compileInARM) {
                        throw new CLIException("Cannot use options '-p' and '-v' and '-arm' together");
                    }
                    break;

                case "-n": // no check
                    this.check = true;
                    break;

                case "-arm": // ARM
                    this.compileInARM = true;
                    if (this.parsing || this.verification) {
                        throw new CLIException("Cannot use options '-p' and '-v' and '-arm' together");
                    }
                    break;

                case "-d": // debug
                    this.debug += 1;
                    break;

                case "-P": // parallele
                    this.parallel = true;
                    break;

                case "-r": // registers
                    index++;
                    int number;
                    try {
                        number = Integer.parseInt(args[index]);
                    } catch (NumberFormatException ex) {
                        throw new CLIException("Must enter a number after '-r' option");
                    }
                    if (number < 4 || number > 16) {
                        throw new CLIException("The number after '-r' option must be between 4 and 16");
                    }
                    numberRegisters = number;
                    break;

                case "-w": // pas pour l'instant

                default:
                    if (!arg.matches("(.)*.deca")) {
                        throw new CLIException("Filemane must end in '.deca'");
                    }
                    // On verifier que le fichier n'est pas deja dans la liste
                    File newFile = new File(arg);
                    boolean alreadyExists = false;
                    for (File file : sourceFiles) {
                        if (file.getAbsolutePath() == newFile.getAbsolutePath()) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (!alreadyExists) {
                        sourceFiles.add(new File(arg));
                    }
            }
            index++;
        }
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
            case QUIET:
                break; // keep default
            case INFO:
                logger.setLevel(Level.INFO);
                break;
            case DEBUG:
                logger.setLevel(Level.DEBUG);
                break;
            case TRACE:
                logger.setLevel(Level.TRACE);
                break;
            default:
                logger.setLevel(Level.ALL);
                break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

    }

    protected void displayUsage() {
        String usage = String.join(
                System.getProperty("line.separator"),
                "usage: decac [[-p | -v | -arm] [-n] [-r X] [-d]* [-P] <fichier deca>...]",
                "             | [-b]");
        // TODO add -w option ?
        System.out.println(usage);

        String options = String.join(
                System.getProperty("line.separator"),
                System.getProperty("line.separator"),
                "  -b (banner)      : affiche une bannière indiquant le nom de l'équipe",
                "  -p (parse)       : arrête decac après l'étape de construction de l'arbre,",
                "                     et affiche la décompilation de ce dernier (i.e. s'il n'y a",
                "                     qu'un fichier source à compiler, la sortie doit être un",
                "                     programme deca syntaxiquement correct)",
                " -v (verification) : arrête decac après l'étape de vérifications (ne produit",
                "                     aucune sortie en l'absence d'erreur)",
                " -n (no check)     : supprime les tests à l'exécution spécifiés dans les points",
                "                     11.1 et 11.3 de la sémantique de Deca.",
                " -r X (registers)  : limite les registres banalisés disponibles à R0 ... R{X-1},",
                "                     avec 4 <= X <= 16",
                " -d (debug)        : active les traces de debug. Répéter l'option plusieurs fois",
                "                     pour avoir plus de traces.",
                " -P (parallel)     : s'il y a plusieurs fichiers sources, lance la compilation",
                "                     des fichiers en parallèle (pour accélérer la compilation)",
                " -arm (ARM)        : génère du code assembleur qui sera executable sur un",
                "                     processeur ARM-v6",
                System.getProperty("line.separator"));

        System.out.println(options);
    }
}
