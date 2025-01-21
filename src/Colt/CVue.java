package Colt;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.LineBorder;

public class CVue {
    ParametresJeu firstWindow;
    CModele CModele;
    JFrame fenetreJeu;
    int largeur = 1100;
    int hauteur = 700;
    //On définit une bordure noire

    Border blackBorder  = BorderFactory.createLineBorder(Color.black);
    Border redBoredr = new LineBorder(Color.RED, 2);



    public CVue(){
        this.firstWindow = new ParametresJeu(this);



    }

    public  void demarrerJeu(CModele CModele){
        fenetreJeu = new JFrame("Colt Express");
        fenetreJeu.setSize(new Dimension(largeur, hauteur));
        fenetreJeu.setVisible(true);
        fenetreJeu.setLayout(null);
        this.CModele = CModele;
        //this.CModele.train = new Train(this.CModele);

        JPanel top = new JPanel(null);
        top.setBorder(blackBorder);
        //top.setBounds(20, 20, 400, 145);

        fenetreJeu.add(top);



        VueJoueurs vueJoueur = new VueJoueurs(CModele, this);
        //vueJoueur.setBounds(70, 70, 70, 70);

        fenetreJeu.add(vueJoueur);

        CModele.addObserver(vueJoueur);

        VueBoutons vueBoutons = new VueBoutons(CModele, this);

        fenetreJeu.add(vueBoutons);







        VueTrain vueTrain = new VueTrain(CModele, this);

        fenetreJeu.add(vueTrain);
        CModele.addObserver(vueTrain);

        CModele.notifyObservers();


       // System.out.println(CModele);
    }



}

interface Observer {
    /**
     * Un observateur doit posséder une méthode [update] déclenchant la mise à
     * jour.
     */
    public void update();
    /**
     * La version officielle de Java possède des paramètres précisant le
     * changement qui a eu lieu.
     */
}
abstract class Observable {
    /**
     * On a une liste [observers] d'observateurs, initialement vide, à laquelle
     * viennent s'inscrire les observateurs via la méthode [addObserver].
     */
    private ArrayList<Observer> observers;
    public Observable() {
        this.observers = new ArrayList<Observer>();
    }
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Lorsque l'état de l'objet observé change, il est convenu d'appeler la
     * méthode [notifyObservers] pour prévenir l'ensemble des observateurs
     * enregistrés.
     * On le fait ici concrètement en appelant la méthode [update] de chaque
     * observateur.
     */
    public void notifyObservers() {
        for(Observer o : observers) {
            o.update();
        }
    }
}

