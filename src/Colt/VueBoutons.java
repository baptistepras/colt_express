package Colt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

enum Boutons{GAUCHE, DROITE, HAUT, BAS, SUIVANT, BRAQUER, TIRER};

public class VueBoutons extends JPanel{

    CVue vue;
    CModele cModele;
    JButton leftButton;
    JButton rightButton;
    JButton topButton;
    JButton bottomButton;
    JButton suivant;
    JButton braquerButton;
    JButton tirerButton;

    JButton viderButton;


    FileAction file;
    public VueBoutons(CModele cModele, CVue vue){
        super();
        this.cModele = cModele;
        this.vue = vue;
        setLayout(null);
        //(vue.blackBorder);
        setBounds(20, 20 , 150, 330);
        creerBoutons();
        creerReactionsBoutons();
         file = new FileAction();

    }

    public  void resetAction(){
        this.file = new FileAction();
    }

    public void creerBoutons(){
        ImageIcon iconleft = new ImageIcon("src/Colt/left.png");
        ImageIcon iconright = new ImageIcon("src/Colt/right.png");
        ImageIcon icontop = new ImageIcon("src/Colt/up.png");
        ImageIcon iconbottom = new ImageIcon("src/Colt/down.png");

        leftButton = new JButton(iconleft);
        leftButton.setBounds(0, 100, 50, 50);
        leftButton.setContentAreaFilled(false);

        rightButton = new JButton(iconright);
        rightButton.setBounds(100, 100, 50, 50);
        rightButton.setContentAreaFilled(false);

        topButton = new JButton(icontop);
        topButton.setBounds(50, 50, 50, 50);
        topButton.setContentAreaFilled(false);

        bottomButton = new JButton(iconbottom);
        bottomButton.setBounds(50, 100, 50, 50);
        bottomButton.setContentAreaFilled(false);

      //  leftButton = new JButton(iconleft);
       // leftButton.setBounds(0, 70, 50, 50);
        //leftButton.setContentAreaFilled(false);
        suivant = new JButton("Joueur suivant ");
        suivant.setBounds(0, 0, 150, 50);

        braquerButton = new JButton("Braquer");
        braquerButton.setBounds(0,150, 150, 50);

        tirerButton = new JButton("Tirer");
        tirerButton.setBounds(0,200, 150, 50);

        viderButton = new JButton("Reinitialiser actions");
        viderButton.setBounds(0,280, 150, 50);

        add(suivant);
        add(braquerButton);
        add(tirerButton);
        add(leftButton);
        add(rightButton);
        add(topButton);
        add(bottomButton);
        add(viderButton);

    }

    public void creerReactionsBoutons(){
        suivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n =  cModele.numeroJoueur;
                cModele.numeroJoueur = (cModele.numeroJoueur +1 )%(cModele.nbBandits);

                if (n == cModele.nbBandits - 2){
                    suivant.setText("Terminer tour");
                }else{

                    suivant.setText("Joueur suivant");}

                cModele.actionHandler.addActions(file);

                if (n == cModele.nbBandits - 1){
                        cModele.console.add("-----------------------------------");
                         cModele.console.add("Tour "+cModele.tour + " : ");
                         cModele.tour++;
                        cModele.actionHandler.joueActions();

                    cModele.actionHandler.reset();
                    cModele.train.marshall.action();
                }

                file = new FileAction();

                cModele.notifyObservers();

            }
        });


        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    if (file.GetSize() < cModele.nbActions){

                        Bandit b = cModele.getBandit(cModele.numeroJoueur);


                        file.enfiler(new Action(typeAction.AVANCER, b, cModele));


                        //cModele.notifyObservers();
                    }

            }
        });


        topButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (file.GetSize() < cModele.nbActions) {
                    Bandit b = cModele.getBandit(cModele.numeroJoueur);
                    file.enfiler(new Action(typeAction.MONTER, b, cModele));


                    //cModele.notifyObservers();
                }
            }
        });

        bottomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (file.GetSize() < cModele.nbActions) {
                    Bandit b = cModele.getBandit(cModele.numeroJoueur);
                    file.enfiler(new Action(typeAction.DESCENDRE, b, cModele));


                    //cModele.notifyObservers();
                }
            }
        });

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (file.GetSize() < cModele.nbActions) {
                    Bandit b = cModele.getBandit(cModele.numeroJoueur);

                    file.enfiler(new Action(typeAction.RECULER, b, cModele));


                    //cModele.notifyObservers();
                }
            }
        });

        viderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = new FileAction();
            }
        });


        braquerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file.GetSize() < cModele.nbActions) {
                    Bandit b = cModele.getBandit(cModele.numeroJoueur);
                    file.enfiler(new Action(typeAction.BRAQUER, b, cModele));
                    //cModele.notifyObservers();
                }
            }
        });

        tirerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file.GetSize() < cModele.nbActions){
                    Bandit b = cModele.getBandit(cModele.numeroJoueur);
                    file.enfiler(new Action(typeAction.TIRER, b, cModele));
                   // cModele.notifyObservers();
                }
            }
        });
    }

}

