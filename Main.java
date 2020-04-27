/**
 * Main Class. Here is the main method and all the GUI settings
 * The game simulates the fight between the Gladiators. Creates a result table, that is going to be updated after the each round
 **/

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{


    private com.company.KampfStatTabelle tabelle = new com.company.KampfStatTabelle();

    public JButton newTab = new JButton("New Game");
    public JButton resetTab = new JButton("Reset");
    public JButton saveTab = new JButton("Save");
    public JButton loadTab = new JButton("Load");
    public JButton newGladiatorTab = new JButton("New Gladiator");
    public JButton deleteGladiatorTab = new JButton("Delete Gladiator");
    public JButton newFightTab = new JButton("New Fight");
    public JTextArea resultTable = new JTextArea(1,20);
    public JScrollPane scrollPane = new JScrollPane(resultTable);


    public Main (String titel){

        super(titel);

        saveTab.addActionListener(new ActionListener() {    //SAVE   DONE
            @Override
            public void actionPerformed(ActionEvent e) {
                tabelle.save();
                System.out.println("Saved");
                JOptionPane.showMessageDialog(null,"Saved!","Saved!",JOptionPane.INFORMATION_MESSAGE);
                resultTable.setText(tabelle.write());
            }
        });

        loadTab.addActionListener(new ActionListener() {    //LOAD   DONE
            @Override
            public void actionPerformed(ActionEvent e) {
                tabelle.load();
                System.out.println("Loaded");
                JOptionPane.showMessageDialog(null,"Loaded!","Loaded!",JOptionPane.INFORMATION_MESSAGE);
                resultTable.setText(tabelle.write());
            }
        });

        resetTab.addActionListener(new ActionListener() {  // RESET  DONE
            @Override
            public void actionPerformed(ActionEvent e) {
                tabelle.reset();
                System.out.println("Reseted");
                if (JOptionPane.showConfirmDialog(null,"Do you really want to reset the game?","Reset the game?",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE)== JOptionPane.YES_OPTION){

                    JOptionPane.showMessageDialog(null,"You reseted the game!","Game reseted!",JOptionPane.INFORMATION_MESSAGE);
                    tabelle.reset();
                    resultTable.setText(tabelle.write());

                }
            }
        });

        newTab.addActionListener(new ActionListener() {     // NEW TABLE
            @Override
            public void actionPerformed(ActionEvent e) {

                if (JOptionPane.showConfirmDialog(null,"Do you really want to start a new game?","New game?",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE)== JOptionPane.YES_OPTION){

                    JOptionPane.showMessageDialog(null,"You started a new game!","New game!",JOptionPane.INFORMATION_MESSAGE);
                    tabelle.newTable();
                    resultTable.setText(tabelle.write());

                }
            }
        });

        newGladiatorTab.addActionListener(new ActionListener() {    //ADD    DONE/Некоректное отображение
            @Override
            public void actionPerformed(ActionEvent e) {

                String newGladName = JOptionPane.showInputDialog(null,"Enter the name of a new Gladiator",JOptionPane.QUESTION_MESSAGE);
                com.company.GladStat gladiator = new com.company.GladStat(newGladName);
                JOptionPane.showMessageDialog(null,gladiator.name + " is now in the game!","Gladiator created!",JOptionPane.INFORMATION_MESSAGE);

                tabelle.add(gladiator);
                resultTable.setText(tabelle.write());

            }
        });


        deleteGladiatorTab.addActionListener(new ActionListener() {  //DELETE   DONE
            @Override
            public void actionPerformed(ActionEvent e) {

                String toDelete = (String) JOptionPane.showInputDialog(null,"Choose a Gladiator to delete"," ",JOptionPane.QUESTION_MESSAGE,null,tabelle.names,tabelle.names);
                com.company.GladStat toDeleteGlad = tabelle.findByName(toDelete);
                tabelle.delete(toDeleteGlad);
                JOptionPane.showMessageDialog(null,toDeleteGlad.name + " left our game!","Gladiator deleted!",JOptionPane.INFORMATION_MESSAGE);
                resultTable.setText(tabelle.write());

            }
        });



        newFightTab.addActionListener(new ActionListener() {   //FIGHT!    DONE
            @Override
            public void actionPerformed(ActionEvent e) {

                com.company.GladStat first = (com.company.GladStat) JOptionPane.showInputDialog(null,"Choose a first Gladiator"," ",JOptionPane.QUESTION_MESSAGE,null, tabelle.gladiators,tabelle.gladiators[0]);
                com.company.GladStat second = (com.company.GladStat) JOptionPane.showInputDialog(null,"Choose a second Gladiator"," ",JOptionPane.QUESTION_MESSAGE,null, tabelle.gladiators,tabelle.gladiators[0]);


                if (first.equals(second)){

                    JOptionPane.showMessageDialog(null,"Gladiator can't fight with himself!","Error!",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if ((first.live==false)||(second.live==false)){

                    JOptionPane.showMessageDialog(null,"One of the Gladiators is already dead!","Error!",JOptionPane.ERROR_MESSAGE);
                    return;

                }

                JOptionPane.showMessageDialog(null,"This is going to a fight between "+first.name + " and " + second.name+ "!","The fight has begun!",JOptionPane.INFORMATION_MESSAGE);

                tabelle.fight(first,second);
                resultTable.setText(tabelle.write());

            }
        });

        add(newTab, BorderLayout.WEST);
        add(resetTab, BorderLayout.WEST);
        add(saveTab, BorderLayout.WEST);
        add(loadTab, BorderLayout.WEST);
        add(newGladiatorTab, BorderLayout.WEST);
        add(deleteGladiatorTab, BorderLayout.WEST);
        add(newFightTab, BorderLayout.WEST);
        add(scrollPane, BorderLayout.SOUTH);

        setLayout (new BorderLayout());
        GridLayout grid = new GridLayout(5,1);
        setLayout(grid);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,500);

    }


    public static void main(String[] args) {

        new Main("Gladiators");

    }

}
