package org.example;

import javax.swing.*;
import java.awt.*;

public class Snake {
    JLabel hijo;
    int lastPosX = 0 ;
    int lastPosY = 0;

    public Snake(int x, int y){
        hijo = new JLabel();
        setLocate(x, y);
        hijo.setBackground(Color.black);
        hijo.setOpaque(true);
    }

    public void setLocation(int x, int y){
        lastPosX = hijo.getX();
        lastPosY = hijo.getY();

        hijo.setLocation(x, y);
    }

    public void setLocate(int x, int y){
        hijo.setBounds(x, y, 30, 30);
    }

    public int getLastPosX() {
        return lastPosX;
    }

    public int getLastPosY() {
        return lastPosY;
    }

    public JLabel getHijo(){
        return hijo;
    }
}
