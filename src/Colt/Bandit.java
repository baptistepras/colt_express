package Colt;

import java.util.ArrayList;


public class Bandit extends Humain {
    String nom;
    private ArrayList<Butin> butins;
    int ballesRestantes;
    CModele cModele;

    public Bandit(String nom, Coord c, CModele t){
        super(nom, TypeEntite.BANDIT, c, t);
        this.nom = nom;
        this.humain = true;
        this.butins = new ArrayList<Butin>();
        this.ballesRestantes = t.nbBalles;
        this.cModele = t;
    }




    public void ajouterButin(Butin butin){

        this.butins.add(butin);
    }
    @Override
    public Butin lacherButin(){
        //si on a un ou plusieurs butins
        //on en lache un au hasard
        int N = butins.size();
        Butin b = null;
        if (N > 0){
            int i = cModele.randomGenerator.nextInt(0, N);
            b = butins.remove(i);
            cModele.train.butins.add(b);
        }
        cModele.console.add(this.affiche() + " lache un(e) "+b.affiche() + "a la position : "+this.coord) ;


        return b;
    }

    @Override
    public boolean aButin(){
        return this.butins.size() != 0 ;
    }



    public int getNombreButins(){
        return this.butins.size();
    }

    public ArrayList<Butin> getButins(){
        return this.butins;
    }

    public  Butin getButin(int i){
        return this.getButins().get(i);
    }

    public int getMoney(){
        int total = 0;
        if (butins.isEmpty()){
            return 0;
        }
        for (Butin b: butins){
            total += b.getValeur();
        }
        return total;
    }


    public void bouger(Direction d){
        this.coord.bouger(d);
        int N = this.getNombreButins();
        //on doit également bouger tous les butins
        for (int i = 0; i < N ; i++){
            this.getButin(i).coord = this.coord;
        }

        this.cModele.train.visited[coord.getIndice()] = true;

    }

    public void braquer(){
        int numeroTrain = this.coord.getIndice();

        ArrayList<Butin> buts =
        (this.coord.estInterieur()) ? cModele.train.getButinsSol(numeroTrain) :cModele.train.getButinsToit(numeroTrain);
        int N = buts.size();

        if (N > 0) {
            int indice = cModele.randomGenerator.nextInt(N);
           Butin b = buts.get(indice);
           //System.out.println(b.coord);
            this.butins.add(b);

            cModele.train.butins.remove(b);

            cModele.console.add(this.affiche() + " recupere un " + b.affiche());

            cModele.console.add(this.affiche() + " possede maintenant " + this.getMoney() +"$.");

            cModele.console.sautLigne();

        }else{
            cModele.console.add(this.affiche() + " ne braque rien, il n'y a rien sur sa position.");
            cModele.console.sautLigne();
        }


    }




    public void tirer(){
        if (this.ballesRestantes > 0) {
            this.ballesRestantes--;
            int numeroTrain = this.coord.getIndice();
            ArrayList<Humain> hums = cModele.train.getHumains(numeroTrain);

            ArrayList<Humain> homm= new ArrayList<>();

            for (Humain h: hums){
                if(h.typeEntite == typeEntite.BANDIT){
                    if (h.aButin()){
                        if (h.coord.estInterieur() == this.coord.estInterieur()){
                            homm.add(h);
                        }
                    }
                }


            }



            int N = homm.size();



            if (N !=0) {
                int i = cModele.randomGenerator.nextInt(N);
                Humain hhu = homm.get(i);
                cModele.console.add(this.affiche() + " tire sur "+hhu.affiche()+ ".");
                Butin b = hhu.lacherButin();



            }else{
                cModele.console.add(this.affiche() + " tire pour rien.");
            }
            cModele.console.add(this.affiche() + " n'a plus que "+this.ballesRestantes + " balles restantes.");
            cModele.console.sautLigne();
        }
    }

    @Override
    public String affiche(){
        return "Bandit "+this.nom;
    }

}
