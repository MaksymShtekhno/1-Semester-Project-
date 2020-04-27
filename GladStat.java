/**
 * This Class represents a GladStat objekt. Here stored the most important variables like name, fights, wins etc.
 * Method compareTo helps us to compare 2 Gladiators to understand with one has a better position in our table.
 * Method resetGladiator helps to drop all the parameters of the Gladiator
 **/


package com.company;

import java.io.Serializable;

public class GladStat implements Serializable {

    protected String name;
    protected int fights;
    protected int wins;
    protected int loses;
    protected double winRate;
    protected boolean live;

    public GladStat(String name){   // Constructor
        this.name = name;
        live = true;
    }

    int compareTo(GladStat gl){

        int firstLetterFirst = this.name.charAt(0);   // First letter of a first name
        int firstLetterSecond = gl.name.charAt(0);    // First letter of a second name

        if (this.live==true&&gl.live==false) return 1;    // First check: Alive or dead?
        else if (this.live==false&&gl.live==true) return -1;
        else if ((this.live==true&&gl.live==true)||(this.live==false&&gl.live==false)){

            if (this.wins>gl.wins) return 1;          // Second check: Who has more wins?
            else if (this.wins<gl.wins) return -1;
            else if (this.wins==gl.wins){

                if (this.winRate>gl.winRate) return 1;          // Third check: Bigger Winrate
                else if (this.winRate<gl.winRate) return -1;
                else if (this.winRate==gl.winRate){


                    if (firstLetterFirst<=firstLetterSecond) return 1;   // Last check: First letter of the name
                    else return -1;
                }

            }
        }
        return 1;   // If everything is the same - first is the winner
    }

    void resetGladiator() {                 // DONE
        this.fights = 0;
        this.wins = 0;
        this.loses = 0;
        this.winRate = 0;
        this.live = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (!(obj instanceof GladStat)) return false;

        GladStat gladStat = (GladStat) obj;

        if (this.name == gladStat.name) return true;
        return false;
    }

}
