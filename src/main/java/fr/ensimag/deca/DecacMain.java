package fr.ensimag.deca;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl39
 * @date 01/01/2023
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);

    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();

        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.out.println(args[0]);
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }

        if (options.getPrintBanner()) {
            System.out.println("\033[1;32m============================================");
            System.out.println("GL8:gr39: Compilateur Decac de l'equipe 39 !");
            System.out.println("============================================ \u001B[0m");
            System.exit(0);
        }

        if (options.getSourceFiles().isEmpty()) {
            options.displayUsage();
            if (args.length == 0) {
                System.exit(0);
            }
            System.exit(1);
        }

        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            long start = System.currentTimeMillis();
            ExecutorService executors = Executors.newFixedThreadPool(java.lang.Runtime.getRuntime().availableProcessors());
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source, options.getCompileInARM());
                executors.execute(compiler);
            }
            executors.shutdown();
            while(!executors.isTerminated());
            LOG.info("Time Taken by concurrent compiling :: "+(System.currentTimeMillis()-start) + " ms ");
        
        } else {
            long start = System.currentTimeMillis();
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source, options.getCompileInARM());
                if (compiler.compile()) {
                    error = true;
                }
            }
            if (!error) {
                LOG.info("Time Taken by normal compiling :: "+(System.currentTimeMillis()-start) + " ms ");
            }
        } 

        System.exit(error ? 1 : 0);
    }
}
