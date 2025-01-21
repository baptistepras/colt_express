package Colt;

import java.util.ArrayList;


//Les actions possibles
enum typeAction{AVANCER, RECULER,MONTER,DESCENDRE,TIRER,BRAQUER}

class FileAction{
    private ArrayList<Action> file;

    public FileAction() {
        this.file = new ArrayList<>();
    }

    void  enfiler(Action A){
        boolean b = this.file.add(A);
    }

    public Action defiler(){

        return this.file.remove(0);

    }

    public void joue(){
        //System.out.println(this.file.size());
        if (this.file.size() > 0){
        Action A = this.defiler();
        A.execute();}else{
            throw  new IndexOutOfBoundsException("FileAction vide");
        }

    }

    public int GetSize(){
        return this.file.size();
    }
}


public class ActionHandler {
    CModele CModele;
    ArrayList<FileAction> actions;
    public ActionHandler(CModele CModele){
        this.CModele = CModele;
        this.actions = new ArrayList<>();
    }



    public void addActions(FileAction A){
        this.actions.add(A);
    }



    /**
     * N'effectue que l'action de la file pour chaque joueur
     * Verifie si toutes les actions ont été effectuées à la fin de l'execution
     */
    public boolean joueActionNiveau()  {
        boolean fini = true;//On considère au début qu'on a fini
        //Cela changera si il reste une file non vi
        for (FileAction F: actions){
            int N = F.GetSize();
            if (N > 0){
                F.joue();
                CModele.notifyObservers();

            }
            if (N > 1){// si il y restait une action au
                fini = false;
            }

        }
        return fini;
    }

    public void joueActions()  {
        boolean fini = false;
        while (!fini){
            fini  = joueActionNiveau();

        }
    }

    public void reset(){
        this.actions = new ArrayList<>();
    }


}

class Action{
    typeAction type;

    Entite actionneur;
    CModele CModele;
    public Action(typeAction type, Entite e, CModele CModele){
        this.type = type;

        this.actionneur = e;
        this.CModele = CModele;
    }

    public void execute(){
        this.CModele.console.addTentative((Humain) this.actionneur, this.type);
        switch (this.type){
            case AVANCER -> this.actionneur.bouger(Direction.AVANT);

            case RECULER ->  this.actionneur.bouger(Direction.ARRIERE);

            case MONTER ->  this.actionneur.bouger(Direction.HAUT);

            case DESCENDRE ->  this.actionneur.bouger(Direction.BAS);

            case TIRER -> ((Humain) this.actionneur).tirer();

            case BRAQUER -> ((Bandit) this.actionneur).braquer();

        }
    }

}

