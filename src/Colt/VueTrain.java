package Colt;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VueTrain extends JPanel implements Observer {

    CModele cModele;
    CVue vue;
    VueWagon[] wagons ;
    int nb_wagons ;

    VueTrain(CModele cModele, CVue vue){
        super(null);
        this.cModele = cModele;
        this.vue  = vue;

        nb_wagons = cModele.nbWagons;
        wagons = new VueWagon[nb_wagons];
        setBounds(200, 20, 800, 350);
       // setBorder(vue.blackBorder);


        creerWagons();
    }

    public  void creerWagons(){
        setLayout(new GridLayout(1, nb_wagons));
        for (int i = 0 ; i < nb_wagons; i++){
            VueWagon p = new VueWagon(cModele, vue, i);
            add(p);
            wagons[i] = p;
        }
    }


    @Override
    public void update() {
        for (VueWagon C : wagons){
            C.Update();
        }
    }
}


class VueWagon extends JPanel{
    CModele cModele;
    CVue vue;
    int numWagon;
    JLabel titre;
    JPanel banditsPanel;
    JPanel butinPanel;
    Font font = new Font("Arial", Font.BOLD, 16);
    JLabel vide = new JLabel(" ");
    JPanel sousPanel ;
    JLabel inconuu = new JLabel("??????");

    VueWagon(CModele cModele, CVue vue, int n){
        super(null);
        inconuu.setForeground(Color.red);

        this.cModele = cModele;
        this.vue = vue;
        this.numWagon = n;


        if (numWagon == cModele.nbWagons - 1){
            titre = new JLabel("Locomotive");

        }else{
            titre = new JLabel("Wagon "+String.valueOf(numWagon + 1));
        }
        titre.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        titre.setVerticalAlignment(SwingConstants.TOP);
        titre.setForeground(Color.blue);
        titre.setBounds(0, 0,(int)(800/cModele.nbWagons), 40 );
        setBorder(vue.blackBorder);
        add(titre);

        sousPanel = new JPanel( );
        sousPanel.setBounds(2, 50, (int)(800/cModele.nbWagons) - 2, 280);
        sousPanel.setLayout(new GridLayout(2, 1));


        createBanditsPanel();
        createBijouxPanel();

        add(sousPanel);



    }

    public  void createBanditsPanel(){
        this.banditsPanel = new JPanel();
        this.banditsPanel.setForeground(Color.red);
        this.banditsPanel.setLayout(new BoxLayout(banditsPanel, BoxLayout.Y_AXIS));
        JLabel debut = new JLabel("Humains : ");
        debut.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        debut.setFont(font);
        debut.setForeground(Color.GREEN);
        this.banditsPanel.add(debut);
        JLabel Vide = new JLabel("      ");
        this.banditsPanel.add(debut);
        JLabel interieur = new JLabel("Interieur : ");
        JLabel toit = new JLabel("Toit : ");
        //interieur.setHorizontalAlignment(SwingConstants.CENTER);
        //toit.setHorizontalAlignment(SwingConstants.CENTER);


        ArrayList<Humain> humains = cModele.train.getHumains(numWagon);



        this.banditsPanel.add(toit);


        for (Humain h: humains) {
            if (!h.coord.estInterieur()){
                JLabel label = new JLabel(h.nom );
                label.setForeground(Color.green);
                this.banditsPanel.add(label);}
        }
        this.banditsPanel.add(Vide);

        this.banditsPanel.add(interieur);

        for (Humain h: humains) {
            if (h.coord.estInterieur()){
                JLabel label = new JLabel(h.nom );
                label.setForeground(Color.red);
                this.banditsPanel.add(label);}
        }


        sousPanel.add(banditsPanel);




    }

    public void ajouterInconnu(){
        this.butinPanel.add(inconuu);
    }

    public void ajouterVide1(){
        butinPanel.add(vide);
    }

    public void createBijouxPanel(){
        butinPanel = new JPanel();
        butinPanel.setForeground(Color.green);
        butinPanel.setLayout(new BoxLayout(butinPanel, BoxLayout.Y_AXIS));
        JLabel debut = new JLabel("Butins : ");
        debut.setFont(font);
        butinPanel.add(debut);
        ArrayList<Butin> butins = cModele.train.getButins(this.numWagon);
        sousPanel.add(butinPanel);


        JLabel inconuu = new JLabel("??????");
        inconuu.setForeground(Color.red);
        //JLabel Vide = new JLabel(" ");

        JLabel interieur = new JLabel("Interieur : ");
        JLabel toit = new JLabel("Toit : ");

        //Si Aucun bandit n'a visité le wagon  on n'affiche pas ce qu'il y a dedans




        interieur.setHorizontalAlignment(SwingConstants.CENTER);
        toit.setHorizontalAlignment(SwingConstants.CENTER);

        if (cModele.train.visited[numWagon]){

            this.butinPanel.add(toit);


            for (Butin h: butins) {
                if (!h.coord.estInterieur()){
                    JLabel label = new JLabel(h.nom + " "+ h.getValeur() + "$");
                    label.setForeground(Color.red);

                    this.butinPanel.add(label);}
            }


            this.butinPanel.add(interieur);

            for (Butin h: butins) {
                if (h.coord.estInterieur()){
                JLabel label = new JLabel(h.nom + " "+ h.getValeur() + "$");
                    label.setForeground(Color.orange);


                this.butinPanel.add(label);}
            }





        }else{
            this.butinPanel.add(toit);
            ajouterInconnu();

            this.butinPanel.add(interieur);
            this.butinPanel.add(inconuu);


        }

        sousPanel.add(butinPanel);
    }



    public  void Update(){
        sousPanel.remove(butinPanel);
        sousPanel.remove(banditsPanel);
        //remove(butinPanel);


        //sousPanel = new JPanel( );
        //sousPanel.setBounds(2, 50, (int)(800/cModele.nbWagons) - 2, 280);
        //sousPanel.setLayout(new BoxLayout(  sousPanel, BoxLayout.Y_AXIS));


        createBanditsPanel();
        createBijouxPanel();

        add(sousPanel);



    }
}