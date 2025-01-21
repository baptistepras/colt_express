package Colt;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class Slider extends JPanel {
    JSlider slider;
    JLabel label;
    String labelName;
    int defaultValue;
    public Slider(int min, int max, int spacing, int smallSpacing, String labelName, int defaultValue) {
        setLayout(new GridLayout(2, 1));
        //slider.setBounds(20, 20, 250, 50);
        slider = new JSlider(min, max);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(spacing);
        slider.setMinorTickSpacing(smallSpacing);
        this.defaultValue = defaultValue;

        slider.setValue(defaultValue);
        slider.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Calcul de la valeur correspondant à la position de la souris
                int value = slider.getMinimum() + (int) ((double) (e.getX() - slider.getInsets().left) / slider.getWidth() * (slider.getMaximum() - slider.getMinimum()));

                // Définition de la nouvelle valeur du slider
                slider.setValue(value);
            }
        });

        this.labelName = labelName;
        label = new JLabel(labelName +" : "+ slider.getValue());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);


        slider.setSize(100, 40);
        add(slider);
    }
    public int getValeur(){return this.slider.getValue();};
    public  void reset(){this.slider.setValue(this.defaultValue);}
    public void updateLabel() {
        label.setText(this.labelName +  " : " + slider.getValue());
    }
}

class TextSelect extends JPanel{
    JTextField textField;
    String base;
    Color C;
    public TextSelect(String nomBase, Color color){
       String base = nomBase;
       Color C = color;
       JTextField textField = new JTextField();

       add(textField);
    }

    public  void reset(){
        this.textField.setText(this.base);
    }
}


class ParametresJeu extends JFrame implements ChangeListener {
    Slider wagonSlider;

    Slider ballesSlider;
    Slider nombreButinSlider;
    Slider nbActionsSlider;
    Slider minValeuSlider;
    Slider maxValeuSlider;
    //Les valeurs de bases du jeu
    int nbWagons = 5;
    int nbActions = 2;
    int nbBalles = 6;
    int nbButin = 4;
    int minButin = 0;
    int maxButin = 500;
    ArrayList<TextSelect> banditsChoosers;
    JPanel sub;
    int nbBandits = 2; //Minimum 2 maximum
    String[] names = {"Raphael","Baptiste","Martin","Vladimir","Javier","Hugo","Alex","Miguel"};
    CVue vue;

    public ParametresJeu(CVue vue) {
        setTitle("Paramètres du jeu");

        this.vue = vue;
        setLayout(null);
        setSize(1100, 700);

        Border blackline = BorderFactory.createLineBorder(Color.black);
        Border blueline = BorderFactory.createLineBorder(new Color(51, 153,255));
        JPanel top = new JPanel(null);
        top.setBorder(blackline);
        top.setBounds(20, 20, 400, 145);

        JLabel text1 = new JLabel("Modifiez les valeurs de base si vous le souhaitez.");
        text1.setBounds(0, 0, 400, 35);

        JLabel text2 = new JLabel("Appuyez sur le bouton démarrer pour commencer");
        text2.setBounds(0, 35, 400, 35);

        JLabel text3 = new JLabel("Appuyez sur le bouton reset pour revenir aux valeurs de base");
        text3.setBounds(0, 70, 400, 35);
        JButton demarrer = new JButton("Démarrer");
        JButton reset = new JButton("Reset");

        demarrer.setBounds(0, 105, 200, 35);
        reset.setBounds(200, 105, 200, 35);
        text1.setHorizontalAlignment(SwingConstants.CENTER);
        text2.setHorizontalAlignment(SwingConstants.CENTER);
        text3.setHorizontalAlignment(SwingConstants.CENTER);

        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                blackline, "title");
        title.setTitleJustification(TitledBorder.CENTER);


        top.add(text1);
        top.add(text2);
        top.add(text3);
        top.add(demarrer);
        top.add(reset);
        add(top);

        JPanel valuesChanger = new JPanel();
        valuesChanger.setBounds(450, 20, 580, 600);
        valuesChanger.setBorder(top.getBorder());
        valuesChanger.setLayout(new GridLayout(3, 2));

        wagonSlider = new Slider(2, 8, 2, 1, "Nombre de wagons (en comptant la locomotive)", nbWagons);
        wagonSlider.slider.addChangeListener(this);
        wagonSlider.setBorder(blueline);


        nbActionsSlider = new Slider(1, 6,1,1,"Actions max par joueur", nbActions);
        nbActionsSlider.slider.addChangeListener(this);
        nbActionsSlider.setBorder(blueline);


        //wagonSlider.slider.setVisible(true);
        ballesSlider = new Slider(1, 10,2,1,"Balles par joueur",nbBalles);
        ballesSlider.slider.addChangeListener(this);
        ballesSlider.setBorder(blueline);

        nombreButinSlider = new Slider(1, 8, 1, 1, "Nombre de butins max par wagons",nbButin);
        nombreButinSlider.slider.addChangeListener(this);
        nombreButinSlider.setBorder(blueline);

        minValeuSlider = new Slider(0, 500, 100, 50, "Valeur minimale d'une bourse",minButin);
        //minValeuSlider.slider.addChangeListener(this);
        minValeuSlider.slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (maxValeuSlider.slider.getValue() < minValeuSlider.slider.getValue()){
                    maxValeuSlider.slider.setValue(minValeuSlider.slider.getValue());
                }
                //maxValeuSlider.updateLabel();
            }
        });
        minValeuSlider.setBorder(blueline);

        maxValeuSlider = new Slider(0, 500, 100, 50, "Valeur maximale d'une bourse", maxButin);
        maxValeuSlider.slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (maxValeuSlider.slider.getValue() < minValeuSlider.slider.getValue()){
                    minValeuSlider.slider.setValue(maxValeuSlider.slider.getValue());
                }
                //minValeuSlider.updateLabel();
            }
        });
        maxValeuSlider.setBorder(blueline);
        //reset();

        minValeuSlider.slider.addChangeListener(this);
        maxValeuSlider.slider.addChangeListener(this);


        valuesChanger.add(wagonSlider);
        valuesChanger.add(nbActionsSlider);
        valuesChanger.add(ballesSlider);
        valuesChanger.add(nombreButinSlider);
        valuesChanger.add(minValeuSlider);
        valuesChanger.add(maxValeuSlider);

        add(valuesChanger);

        JPanel banditsSelect = new JPanel();
        banditsSelect.setBounds(20, 180, 400, 450);


        JLabel j1= new JLabel("Entrez les noms de 2 à 8 joueurs");
        JLabel j2= new JLabel("Un nom sera attribué à un joueur même si aucun nom n'a été entré.");
        banditsSelect.setLayout(null);
        j1.setBounds(0,0,400, 30);
        j1.setHorizontalAlignment(SwingConstants.CENTER);
        j2.setBounds(0,30,400, 30);
        j2.setHorizontalAlignment(SwingConstants.CENTER);
        banditsSelect.add(j1);
        banditsSelect.add(j2);

        JButton nouveauBandit = new JButton("Ajouter joueur");
        nouveauBandit.setBounds(0,0, 200, 78);
        JButton enleverBandit = new JButton("Retirer joueur");
        enleverBandit.setBounds(200,0, 200, 78);
        //banditsSelect.setLayout(new GridLayout(6, 2));
        banditsSelect.add(nouveauBandit);
        banditsSelect.add(enleverBandit);

        //On initialise les bandits
        sub = new JPanel();
        sub.setLayout(null);
        sub.setBounds(0, 60, 400, 390);
        sub.add(nouveauBandit);
        sub.add(enleverBandit);
        initBanditChooser();


        demarrer.addChangeListener(this);
        demarrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //On prend les valeurs selectionees
                ArrayList<String> Names = new ArrayList<>();
                for (int i = 0; i < nbBandits; i++){
                    JTextField t = (JTextField) sub.getComponent(i+2);
                    String nom =  t.getText();
                    if (nom == ""){
                        nom = names[i];
                    }
                    Names.add(nom);
                }
                vue.demarrerJeu(new CModele(Names, nbBalles, nbWagons, minButin, maxButin, nbActions, nbButin));
                dispose();
                ;
            }
        });

        banditsSelect.add(sub);
        banditsSelect.setBorder(blackline);
        add(banditsSelect);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                while (nbBandits > 0){

                    sub.remove(nbBandits+1);
                    nbBandits--;
                    sub.updateUI();
                }
                initBanditChooser();
            }
        });

        nouveauBandit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nbBandits < 8){
                    //System.out.println("ajoute");
                    JTextField t = new JTextField();
                    nbBandits++;
                    t.setText(names[nbBandits-1]);
                    t.setBounds(200*((nbBandits-1)%2), 78+78*((nbBandits-1)/2), 200, 78);
                    sub.add(t);
                }
            }
        });

        enleverBandit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (nbBandits > 2){
                   sub.remove(nbBandits+1);
                   nbBandits--;
                   sub.updateUI();
               }
            }
        });



    }

    public  void initBanditChooser(){

        for (int i = 0; i < 2; i++){
            JTextField t = new JTextField();
            t.setText(names[i]);
            t.setBounds(200*i, 78, 200, 78);
            sub.add(t);
        }
        nbBandits = 2;

    }

    public void reset(){
        wagonSlider.reset();
        nbActionsSlider.reset();
        ballesSlider.reset();
        nombreButinSlider.reset();
        minValeuSlider.reset();
        maxValeuSlider.reset();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        wagonSlider.updateLabel();
        nbWagons = wagonSlider.getValeur();

        nbActionsSlider.updateLabel();
        nbActions = nbActionsSlider.getValeur();

        ballesSlider.updateLabel();
        nbBalles = ballesSlider.getValeur();

        nombreButinSlider.updateLabel();
        nbButin = nombreButinSlider.getValeur();

        maxValeuSlider.updateLabel();
        maxButin = maxValeuSlider.getValeur();

        minValeuSlider.updateLabel();
        minButin = minValeuSlider.getValeur();

       /* if (minValeuSlider.slider.getValueIsAdjusting()){
            if (minValeuSlider.slider.getValue() > maxValeuSlider.slider.getValue()){
                maxValeuSlider.slider.setValue(minValeuSlider.slider.getValue());
            }}
        if (maxValeuSlider.slider.getValueIsAdjusting()){
            if (maxValeuSlider.slider.getValue() < minValeuSlider.slider.getValue()){
                minValeuSlider.slider.setValue(maxValeuSlider.slider.getValue());
            }
        }*/

    }


}
