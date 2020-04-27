/**
 * GladStat Array contains the information about all Gladiators in our game
 * currentElementPosition is a counter, that helps us to iterate through the array
 * Contains all the methods to control the game, update the table
 **/

package com.company;

import java.io.*;
import java.util.Arrays;

public class KampfStatTabelle implements Serializable {


    protected com.company.GladStat[] gladiators = new com.company.GladStat[20];
    protected String[] names = new String[20];

    private int currentElementPosition = 0;
    private int currentElementPositionNames = 0;


    public void add(com.company.GladStat gladiator){                            //Add new Gladiator to our game
        if (currentElementPosition == gladiators.length)           // If the array has 20 elements -> double the capacity
            doppeln();
        gladiators[currentElementPosition++] = gladiator;          // add an element to 'gladiators' array
        names[currentElementPositionNames++] = gladiator.name;

    }


    public int find(Object obj){                                //Find the the index of a Gladiator

        for (int i = 0; i <gladiators.length ; i++) {
            if (obj.equals(gladiators[i])){
                return i;
            }
        }
        return -1;

    }

    public com.company.GladStat findByName (String name){   //Find the Gladiator by his name

        for (int i = 0; i < names.length; i++) {
            if (name.equals(names[i])){
                return gladiators[i];
            }
        }
        return null;
    }


    public void newTable(){                     // Creates a new Table to start a new game

        gladiators = new com.company.GladStat[20];
        currentElementPosition = 0;
        names = new String[20];
        currentElementPositionNames = 0;

    }

    public String write(){                       //Writes the current game info in the table
        StringBuilder sb = new StringBuilder();
        sort();
        for (int i = 0; i < currentElementPosition; i++) {
            sb.append(gladiators[i].name + "| " + gladiators[i].fights + "| " + gladiators[i].wins + "| " + gladiators[i].loses + "| " + gladiators[i].winRate + "| " + gladiators[i].live + "\n");
        }
        return sb.toString();
    }

    public void delete (com.company.GladStat gladiator){                //Deletes the Gladiator from the Array
        int index = find(gladiator);
        for (int i = index; i < currentElementPosition; i++) {
            gladiators[i] = gladiators[i+1];
            names[i] = names[i+1];
        }
        currentElementPosition--;
        currentElementPositionNames--;
    }

    public void reset(){                                    //Resets the the statistics about the Gladiators, but keeps the table!
        for (int i = 0; i < currentElementPosition; i++) {
            gladiators[i].resetGladiator();
        }
    }


    public void fight(com.company.GladStat gladiator1, com.company.GladStat gladiator2){   //Simulates a fight between 2 GLadiators

        Wurfel wurfel = new Wurfel(6);      //A new cube with 6 sides

        int result = wurfel.wuerfle();

        if (result == 1 || result == 2) {       //  1 oder 2 oder 3 - 1. Glad won
            gladiator1.fights++;
            gladiator1.wins++;
            gladiator2.fights++;
            gladiator2.loses++;
            gladiator1.live = true;
            gladiator2.live = true;
        }

        if (result == 3) {                         // If 3 - second Gl dies
            gladiator1.fights++;
            gladiator1.wins++;
            gladiator2.fights++;
            gladiator2.loses++;
            gladiator1.live = true;
            gladiator2.live = false;
        }

        if (result == 4 || result == 5) {       // 4;5 or 6  - second Glad wins
            gladiator2.fights++;
            gladiator2.wins++;
            gladiator1.fights++;
            gladiator1.loses++;
            gladiator1.live = true;
            gladiator2.live = true;
        }

        if (result == 6) {                     // If 6 - first Gl dies
            gladiator2.fights++;
            gladiator2.wins++;
            gladiator1.fights++;
            gladiator1.loses++;
            gladiator1.live = false;
            gladiator2.live = true;
        }


        if (gladiator1.loses != 0) {
            gladiator1.winRate = gladiator1.wins / gladiator1.loses;
        } else gladiator1.winRate = gladiator1.wins;

        if (gladiator2.loses != 0) {
            gladiator2.winRate = gladiator2.wins / gladiator2.loses;
        } else gladiator2.winRate = gladiator2.wins;


        sort();         // sort the table after the round
    }


    public void sort (){            // Sorts the table with the algorithm from the GladStat class

        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < currentElementPosition; i++) {
                if (gladiators[i].compareTo(gladiators[i - 1]) == 1) {
                    swap(gladiators, i, i - 1);
                    needIteration = true;
                }
            }
        }
    }

    private void swap(com.company.GladStat[] array, int gl1, int gl2) { //Helps to sort the array
        com.company.GladStat tmp = array[gl1];
        array[gl1] = array[gl2];
        array[gl2] = tmp;
    }

    public void doppeln() {

        int increasedSize = gladiators.length * 2;
        gladiators = Arrays.copyOf(gladiators, increasedSize);  // Double the Array size
        names = Arrays.copyOf(names,increasedSize);

    }

    public void save(){             // Saving Mechanism. Works with a help of Serialization.

        try {

            FileOutputStream fos1 = new FileOutputStream("tabelle.ser");
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(gladiators);
            oos1.close();

            FileOutputStream fos2 = new FileOutputStream("currentElement.ser");
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos2.writeObject(currentElementPosition);
            oos2.close();

            FileOutputStream fos3 = new FileOutputStream("names.ser");
            ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
            oos3.writeObject(names);
            oos3.close();

            FileOutputStream fos4 = new FileOutputStream("currentElementNames.ser");
            ObjectOutputStream oos4 = new ObjectOutputStream(fos4);
            oos4.writeObject(currentElementPositionNames);
            oos4.close();

        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void load(){                 // Loading Mechanism. Works with a help of Serialization.

        try {

            FileInputStream fis1 = new FileInputStream("tabelle.ser");
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            com.company.GladStat[] GladStatsFromSavedFile = (com.company.GladStat[]) ois1.readObject();
            ois1.close();

            FileInputStream fis2 = new FileInputStream("currentElement.ser");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            int CurrentElementFromSavedFile = (int) ois2.readObject();
            ois2.close();

            FileInputStream fis3 = new FileInputStream("names.ser");
            ObjectInputStream ois3 = new ObjectInputStream(fis3);
            String[] NamesFromSavedFile = (String[]) ois3.readObject();
            ois3.close();

            FileInputStream fis4 = new FileInputStream("currentElementNames.ser");
            ObjectInputStream ois4 = new ObjectInputStream(fis4);
            int CurrentElementNamesFromSavedFile = (int) ois4.readObject();
            ois4.close();

            currentElementPosition = CurrentElementFromSavedFile;

            gladiators = Arrays.copyOf(GladStatsFromSavedFile, gladiators.length);

            names = Arrays.copyOf(NamesFromSavedFile,names.length);

            currentElementPositionNames = CurrentElementNamesFromSavedFile;

        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
