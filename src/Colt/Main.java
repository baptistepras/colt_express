package Colt;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            /** Voici le contenu qui nous intéresse. */
            CVue vue = new CVue();
        });
    }
}
