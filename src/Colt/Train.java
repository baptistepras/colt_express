package Colt;

import java.util.ArrayList;


public class Train{

    int NB_WAGONS;
    ArrayList<Butin> butins;
    ArrayList<Humain> humains;
    CModele CModele;
    ArrayList<String> noms;
    Marshall marshall;


    boolean[] visited ;


    public Butin newButin(int indiceWagon, int i){
        Boolean bijou = CModele.randomGenerator.nextBoolean();
        if (bijou){
            return new Butin("Bijou", new Coord(indiceWagon, true, CModele),500, CModele, i);
        }else{
            int value = CModele.minvaleurButin + CModele.randomGenerator.nextInt(CModele.minvaleurButin + CModele.maxvaleurButin + 1);
            return new Butin("Bourse", new Coord(indiceWagon, true, CModele),value, CModele, i);
        }
    }

    public void init(){



        //Puis pour chaque wagon on place aléatoirement entre 1 et 4butins
        // De valeurs


        //Puis on cree les butins
        //On rappelle qu'il y a un magot dans la locomotive, c'est à dire au bout

    }

    @Override
    public  String toString(){
        String s = "";
        for (Humain H : humains){
            s += H.toString() + "\n";
        }

        for (Butin H : butins){
            s += H.toString() + "\n";
        }

        return s;
    }

    public  Train( CModele CModele) {
        this.CModele = CModele;
        this.NB_WAGONS = CModele.nbWagons;
        this.noms = CModele.noms;



        Coord debut = new Coord(0, true, CModele);
        Coord fin = new Coord(this.NB_WAGONS-1, true, CModele);
        String nom;
        //On créé le marshall
        Humain h = new Marshall(CModele);
        this.marshall = (Marshall) h;
        this.humains = new ArrayList<Humain>();
        this.humains.add(h);

        this.butins = new ArrayList<Butin>();
        //Le magot qui est dans la locomotive
        Butin butin = new Butin("Magot ", fin, 1000, CModele, 0);
        butins.add(butin);


        //On crée chaque bandit
        for (int i = 0; i < noms.size(); i++){
            h = new Bandit(noms.get(i), new Coord(0, true, CModele), CModele);
            this.humains.add(h);
        }




        int nbBijoux;
        int total = 1;
        for (int indiceWagon = 0; indiceWagon <= this.NB_WAGONS-1 ; indiceWagon++){
            nbBijoux = CModele.randomGenerator.nextInt(1, CModele.nbButins+1);
            for (int i = 0 ; i < nbBijoux; i++){
                Butin b = newButin(indiceWagon, total);
                this.butins.add(b);
                total++;
            }
        }

        visited = new boolean[NB_WAGONS];
        visited[0] = true;
        for (int i = 1; i < NB_WAGONS; i++){
            visited[i] = false;
        }




    }

    public ArrayList<Humain> getHumains(int numero){
        ArrayList<Humain> liste = new ArrayList<>();
        for (Humain hum: this.humains){
            if (hum.coord.getIndice() == numero){
                liste.add(hum);
            }
        }
        return liste;
    }


    public ArrayList<Butin> getButins(int numero){
        ArrayList<Butin> liste = new ArrayList<>();
        for (Butin but: butins){
            if (but.coord.getIndice() == numero){
                liste.add(but);
            }
        }
        return liste;
    }

    public ArrayList<Butin> getButinsToit(int numero){
        ArrayList<Butin> liste = new ArrayList<>();
        for (Butin but: butins){
            if (but.coord.getIndice() == numero && (!but.coord.estInterieur())){
                liste.add(but);
            }
        }
        return liste;
    }

    public ArrayList<Butin> getButinsSol(int numero){
        ArrayList<Butin> liste = new ArrayList<>();
        for (Butin but: butins){
            if (but.coord.getIndice() == numero && but.coord.estInterieur()){
                liste.add(but);
            }
        }
        return liste;
    }



}





