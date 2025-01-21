package Colt;

import javax.swing.*;
import java.awt.*;

public class VueJoueurs  extends JPanel implements Observer {
    CModele cModele;
    CVue vue;
    public VueJoueurs(CModele cModele, CVue vue){

        super(null);

        this.cModele = cModele;
        this.vue = vue;
        //Taille choisie 40 % largeur , 60 % hauteur
        this.setBounds(220, 380, (int)(0.6* vue.largeur), 280);
        setLayout(new GridLayout(2, 4));

       //setLayout(null);
        this.setBorder(vue.blackBorder);
        creePanelsJoueur();
        update();


    }

    public void creePanelsJoueur(){
        for (int i = 0; i < cModele.noms.size(); i++){

            PanelJoueur panel = new PanelJoueur(cModele, vue,  i);
            panel.setBorder(vue.blackBorder);
            add(panel);
        }
    }


    @Override
    public void update() {
        for (int i = 0; i < cModele.noms.size(); i++){
            Component C =getComponent(i);
            ((PanelJoueur) C).Update();

        }
    }
}
class PanelJoueur extends JPanel {
    CModele cModele;
    CVue vue;
    int numero;
    JLabel nomVue;
    JLabel posVue;
    JLabel ballesVue;
    JLabel argentVue;
    Bandit bandit;
    public PanelJoueur(CModele cModele, CVue vue,int numero){
        super(null);
        this.cModele = cModele;
        this.numero = numero;
        this.vue = vue;
        setLayout(new GridLayout(4, 1));
        bandit = cModele.getBandit(numero);

        nomVue = new JLabel("Bandit " + bandit.nom);
        posVue = new JLabel("Position : " + bandit.coord.toString());
        ballesVue = new JLabel("Balles restantes : "+String.valueOf(bandit.ballesRestantes));
        argentVue = new JLabel("Butin total : "+String.valueOf(bandit.getMoney()));


        add(nomVue);
        add(posVue);
        add(ballesVue);
        add(argentVue);


    }


    public void Update() {
        if (cModele.numeroJoueur == this.numero){
            this.setBorder(vue.redBoredr);


        }else{
            this.setBorder(vue.blackBorder);
        }

        nomVue.setText("Bandit " + bandit.nom);
        posVue.setText("Position : " + bandit.coord.toString());
        ballesVue.setText("Balles restantes : "+String.valueOf(bandit.ballesRestantes));
        argentVue.setText("Butin total : "+String.valueOf(bandit.getMoney()));


    }
}

