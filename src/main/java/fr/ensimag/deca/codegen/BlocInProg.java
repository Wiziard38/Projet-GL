package fr.ensimag.deca.codegen;

import java.util.HashMap;

public class BlocInProg {
    
    private static HashMap<String, Bloc> listBloc = new HashMap<String, Bloc>();

    public static void addBloc(String name, int line, int register, int place){
        Bloc newBloc = new Bloc(line, register, place);
        listBloc.put(name, newBloc);
    }

    public static Bloc getBlock(String name){
        return listBloc.get(name);
    }

    public static class Bloc{

        private int lineStart;
        private int nbregisterNeeded;
        private int placePileNeeded;

        protected Bloc(int line, int register, int place){
            this.lineStart = line;
            this.nbregisterNeeded = register;
            this.placePileNeeded = place;
        }
    
        public void incrnbRegisterNeeded(int nbRegister){
            if (nbRegister > nbregisterNeeded) {
                nbregisterNeeded = nbRegister;
            }
            
        }
    
        public void incrnbPlacePileNeeded(){
            placePileNeeded += 1;
        }
    
        public int getLineStart(){
            return lineStart;
        }
    
        public int getnbRegisterNeeded(){
            return nbregisterNeeded;
        }
    
        public int getnbPlacePileNeeded(){
            return placePileNeeded;
        }
    }
}
