/**
 * This classs represents the entity of the playing cube. The number of sides could be defined through the constuctor
 **/

package com.company;

public class Wurfel {

    private int seiten;

    public Wurfel(int seiten){
        if ((seiten>=6)&&(seiten<=20)) {
            this.seiten = seiten;
        }
        else {
            throw new IllegalArgumentException("Wurfel darf nur von 6 bis 20 Seiten haben");
        }
    }

    int wuerfle(){     //Simulates the throw of the cube and returns the number of the side

        int number = (int)(Math.random()*seiten);

        if (number==0){
            number++;
        }

        return number;
    }

}
