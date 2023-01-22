package fr.ensimag.deca.codegen;

public class Bloc {

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Bloc) {
            Bloc bloc = (Bloc)o;
            return bloc.getLineStart() == this.getLineStart();
        }
        else {
            return false;
        }
    }
}
