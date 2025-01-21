package Colt;

enum Direction{AVANT, ARRIERE, HAUT, BAS}
enum TypeEntite{BUTIN, BANDIT, MARSHALL
}

/**
 * Une coordonnee sert pour un joueur,
 * Un butin
 * indiceEltTrain fait référence à l'indice dans le train
 * interieur est un booleen qui vaut true si l'entité est à l'intérieur
 * et false si elle est sur le toit
 */
class Coord{

    private int indiceEltTrain;
    private boolean interieur;
    private final CModele CModele;

    /**
     * Le constructeur de base pour une coordonnée
     * @param indiceEltTrain
     * @param interieur
     */
    public Coord(int indiceEltTrain, boolean interieur, CModele CModele){
        this.indiceEltTrain = indiceEltTrain; this.interieur = interieur;
        this.CModele = CModele;
    }

    public int getIndice(){
        return this.indiceEltTrain;
    }

    public  boolean estInterieur(){
        return this.interieur;
    }

    /**
     *
     */
    public void bouger(Direction d){
        switch (d){
            case ARRIERE ->{ if (this.indiceEltTrain > 0){ this.indiceEltTrain--;}}
            case AVANT -> { if (this.indiceEltTrain + 1 < CModele.train.NB_WAGONS) {  this.indiceEltTrain++;}}
            case HAUT -> { this.interieur = false;            }
            case BAS -> { this.interieur = true;
            }
        }
    }
    @Override
    public String toString(){
        String a = "Coordonnee : ";
        if (this.interieur){
            a = "Interieur ";
        }else{
            a = "Toit ";
        }

        return a + "wagon "+ String.valueOf(this.indiceEltTrain + 1) ;
    }

}




/**
 * Une entité est un objet quelconque du jeu,
 * Un joueur, un voyageur ou un butin laissé par terre
 * Une entité possède forcément une position
 */
public abstract class Entite {
    String nom;
    TypeEntite typeEntite;
    Coord coord;
    CModele cModele;
    boolean humain;

    public Entite(String nom, TypeEntite e, Coord c, CModele CModele){
        this.nom = nom;
        this.typeEntite = e;
        this.coord = c;
        cModele = CModele;
    }

    public void bouger(Direction d){

        this.coord.bouger(d);
        if (this.typeEntite == typeEntite.BANDIT){
            cModele.console.add(this.affiche() + " se trouve maintenant a la position : " + this.coord);
            cModele.console.sautLigne();
        }

    }


    @Override
    public String toString(){
        return "Entite " + this.affiche() + this.coord.toString();
    }

    public abstract String affiche();




}

class Humain extends Entite{

    public Humain(String nom, TypeEntite e, Coord c, CModele t) {
        super(nom, e, c, t);
        this.humain = true;
    }
    public  Butin lacherButin(){
        return null;
    }

    public boolean aButin(){
        return false;
    }

    @Override
    public String affiche() {
        return null;
    }

    public void tirer(){

    }



}

class Butin extends Entite{
    int valeur;
    int id;

    private boolean sol;


    public Butin(String nom, Coord C, int value, CModele CModele, int id){
        super(nom, TypeEntite.BUTIN, C, CModele);
        this.valeur = value;
        this.id = id;
        sol = false;

    }
    public void tomber(){
        sol = true;
    }

    public boolean besoinBraquage(){
        return !sol;
    }

    public int getValeur(){
        return valeur;
    }

    @Override
    public String affiche() {
        return nom+" " + String.valueOf(this.getValeur()) + " $ ";
    }


}