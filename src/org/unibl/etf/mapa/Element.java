package org.unibl.etf.mapa;

import java.io.Serializable;

public abstract class Element implements Serializable {
    private int x;
    private int y;
    public boolean uStanici = false;

    public void incrementX(){
        this.x++;
    }

    public void incrementY(){
        this.y++;
    }

    public void decrementX(){
        this.x--;
    }

    public void decrementY(){
        this.y--;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
