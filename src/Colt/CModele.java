package Colt;
import java.util.ArrayList;
import java.util.Random;

public class CModele extends Observable {
    Train train;
    Console console;
    Random randomGenerator = new Random();
    ActionHandler actionHandler = new ActionHandler(this);

    ArrayList<String> noms;
    int nbBalles;
    int nbWagons;
    int minvaleurButin;
    int maxvaleurButin;
    int nbActions;
    int nbButins;
    int nbBandits;

    boolean selectionne;
    int numeroJoueur ;
    int tour = 1;



    public CModele(ArrayList<String> noms, int nbBalles,
                   int nbWagons,
                   int minvaleurButin,
                   int maxvaleurButin,
                   int nbActions,
                   int nbButins
                            ){
        super();
        this.noms = noms;
        this.nbBandits = noms.size();
        this.numeroJoueur = 0;
        this.nbBalles = nbBalles;
        this.nbButins = nbButins;
        this.nbWagons = nbWagons;
        this.minvaleurButin = minvaleurButin;
        this.maxvaleurButin = maxvaleurButin;
        this.nbActions = nbActions;
        this.train = new Train(this);
        this.console = new Console();
        //this.console = new Console();


    }
    @Override
    public String toString(){
        String reponse = "Nom des joueurs : ";
        reponse += noms + "\n";
        reponse += "Nombre wagons : " + this.nbWagons + "\n";
        reponse += "Nombre actions max : " + this.nbActions + "\n";
        reponse += "Nombre butins max par wagon : " + this.nbButins + "\n";
        reponse += "Nombre de balles par bandits : " + this.nbBalles + "\n";
        reponse += "Valeur max d'un bijou : " + this.maxvaleurButin + "\n";
        reponse += "Valeur min d'un bijou : " + this.minvaleurButin + "\n";

        reponse += train;
        return reponse;
    }

    Bandit getBandit(int i){
        return (Bandit) this.train.humains.get(i+1);
    }



}
