package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;


public class MyFrame extends JFrame implements KeyListener {
    JLabel label;
    JLabel puntuacion;
    int puntaje = 0;
    Timer timer;
    JLabel apple;
    int lastKeyCode = 0;
    Random rand = new Random();
    int max = 16;
    int lastPosX = 0;
    int lastPosY = 0;
    int puntajeFinal;
    ImageIcon icon;
    Image image;

    ArrayList<Snake> hijos = new ArrayList<>();


    public MyFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(540,540);
        this.setLayout(null);
        this.addKeyListener(this);
        puntuacion = new JLabel();
        puntuacion.setBounds(400, 0, 100, 25);
        puntuacion.setText("Puntuacion : " + puntaje);
        label = new JLabel();
        apple = new JLabel();
        label.setBounds(0, 0, 30, 30);
        label.setBackground(Color.black);
        label.setOpaque(true);
        apple.setBounds(210, 210, 30, 30);
        icon = new ImageIcon("apple.png");
        image = icon.getImage();
        Image newImg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newImg);
        apple.setIcon(icon);
        this.add(label);
        this.add(puntuacion);
        this.add(apple);


        timer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            moveLastKey();
            verifPuntuacion();
            limiteHorizontal();
            limiteVertical();
            if(puntaje>=3){
                hitsBody();
            }
            if(puntaje>0){
            refreshChildPos();
            }
        }
               });
        timer.start();
        this.setVisible(true);
    }

    public void moveLastKey(){

        lastPosX = label.getX();
        lastPosY = label.getY();
        switch (lastKeyCode){
            case 68: //D
                label.setLocation(label.getX()+30, label.getY());
                break;

            case 83: //S
                label.setLocation(label.getX(), label.getY()+30);
                break;

            case 65: //A
               label.setLocation(label.getX() - 30, label.getY());
                break;

            case 87: //W
                label.setLocation(label.getX(), label.getY() - 30);
                break;
       }
    }

    public void randomLocate(){
        int randomX = rand.nextInt(max);
        int randomY = rand.nextInt(max);
        randomX = randomX*30;
        randomY = randomY*30;
        apple.setLocation(randomX, randomY);
    }


    public void refreshChildPos(){
        int i=1;
        while(i<=puntaje){
            if(i==1) {
                hijos.getFirst().setLocation(lastPosX, lastPosY);
            }
            else {
                int x = hijos.get(i - 2).getLastPosX();
                int y = hijos.get(i - 2).getLastPosY();
                hijos.get(i - 1).setLocation(x, y);
            }
            i++;
        }
    }

    public void agregarHijo(){
        if(puntaje==1){ //Es el primer hijo
            hijos.add(new Snake(lastPosX, lastPosY));
        }
        else {
            int x = hijos.get(puntaje-2).getLastPosX();
            int y = hijos.get(puntaje-2).getLastPosY();

            hijos.add(new Snake(x, y));

        }
        this.add(hijos.get(puntaje-1).getHijo());
    }

    //verifica si la cabeza y la manzana estan en la misma posicion
    public void verifPuntuacion(){
        if (label.getX() == apple.getX() && label.getY() == apple.getY()){
            puntaje++;
            puntuacion.setText("Puntuacion : " + puntaje);
            randomLocate();
            agregarHijo();
        }
    }


    public void limiteHorizontal(){
        if(label.getX() == -30){
            label.setLocation(480, label.getY());
        }
        if(label.getX() == 510){
            label.setLocation(0, label.getY());
        }

    }

    public void limiteVertical(){
        if(label.getY() == -30){
            label.setLocation(label.getX(), 480);
        }
        if(label.getY() == 510){
            label.setLocation(label.getX(), 0);
        }
    }

    public void hitsBody(){
        for (Snake hijo : hijos) {
            // Realizar acciones con cada elemento
            if(label.getX() == hijo.getHijo().getX() && label.getY() == hijo.getHijo().getY()){
                timer.stop();
                puntajeFinal = puntaje;
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(lastKeyCode == 0){
            lastKeyCode = e.getKeyCode();
        }
        if(lastKeyCode == 68 && e.getKeyCode()!=65){
            lastKeyCode = e.getKeyCode();
        }
        if(lastKeyCode == 65 && e.getKeyCode()!=68){
            lastKeyCode = e.getKeyCode();
        }
        if(lastKeyCode == 83 && e.getKeyCode()!=87){
            lastKeyCode = e.getKeyCode();
        }
        if(lastKeyCode == 87 && e.getKeyCode()!=83){
            lastKeyCode = e.getKeyCode();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
