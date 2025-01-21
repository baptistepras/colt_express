package Colt;

public class Marshall extends Humain{

    double nervosite = 0.3;
    CModele cModele;
    public Marshall(CModele CModele) {

        //Coord c = new Coord(train.NB_WAGONS - 1, true, train);
        super("Marshall", TypeEntite.MARSHALL, new Coord(CModele.nbWagons-1,true, CModele), CModele);
        this.cModele = CModele;

    }


    public void action(){

        //si iil y a des bandits dans le meme wagon que lui il lachent tous un butin
        for (Humain h: cModele.train.humains){
            if (h.coord.getIndice() == this.coord.getIndice() && h.coord.estInterieur()){
                if (h.typeEntite == TypeEntite.BANDIT){
                    cModele.console.add("Le marshall tire sur " + h.affiche());
                    h.lacherButin();
                    h.bouger(Direction.HAUT);
                }
            }
        }

        double proba = cModele.randomGenerator.nextDouble();
        int arriere;
        if (proba <= nervosite){

            if (coord.getIndice() == 0){
                arriere = 1;
            }else{
                if (coord.getIndice() == cModele.nbWagons - 1){
                    arriere = 0;
                }else{
                    arriere = cModele.randomGenerator.nextInt(0, 2);
                }
            }



            if (arriere == 0 ){
                this.bouger(Direction.ARRIERE);
                cModele.console.add("Le marshall recule d'un wagon.");
            }else{
                this.bouger(Direction.AVANT);
                cModele.console.add("Le marshall avance d'un wagon.");
            }
            cModele.console.add("Le marshall se trouve maintenant : "+this.coord);
            cModele.console.sautLigne();
        }



    }


    @Override
    public String affiche(){
        return "Le marshall ";
    }

}
