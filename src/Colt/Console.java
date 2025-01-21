package Colt;


import javax.swing.*;
import java.util.ArrayList;

public class Console {
    ArrayList<String> messages;
    boolean Terminal = true;

    public  Console(){
        this.messages = new ArrayList<String>();
    }

    public void add(String s){
        this.messages.add(s);
        if (Terminal){
            System.out.println(s);
        }
    }

    public  void sautLigne(){
        this.add(" ");
    }

    /* Traduit une action tentée par un humain en string
     *
     */
    public String transcrireTentative(Humain e, typeAction A){
        return  switch (A){
            case AVANCER -> e.affiche() + " tente d'avancer d'un wagon";
            case RECULER ->  e.affiche()  + " tente de reculer d'un wagon";

            case MONTER -> e.affiche()  + " tente de monter sur le toit";

            case DESCENDRE -> e.affiche()  + " tente de descendre du toit";

            case TIRER ->  e.affiche()  + " tente de tirer";

            case BRAQUER -> e.affiche()  + " tente un braquage";

        };
    }
    public  void addTentative(Humain e, typeAction A){
        String s = this.transcrireTentative(e, A);
        this.add(s);

    }
    @Override
    public String toString(){
        String result = "";
        for (String s:this.messages){
           result += s + "\n";
        }
        return  result;
    }

}




