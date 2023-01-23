package fr.ensimag.deca.codegen;

/**
     * Classe représentant un bloc (méthode ou programme principale). Il y a l'indice de la ligne de début, le nombre de registres nécessaire et
     * la place dans la pile nécessaire dans le bloc. Ainsi à la fin de l'assemblage du bloc on peut ajouter au début le test sur la pile,
     * push les registres nécessaires et les pop à la fin
     */
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
