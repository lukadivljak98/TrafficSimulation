package org.unibl.etf.kompozicije.lokomotive;

import org.unibl.etf.mapa.Element;

public abstract class Lokomotiva extends Element {
    private int id;
    private int snaga;
    private VrstaPogona vrstaPogona;
    public boolean podNaponom;

    public Lokomotiva(int id, int snaga, VrstaPogona vrstaPogona){
        this.id = id;
        this.snaga = snaga;
        this.vrstaPogona = vrstaPogona;
        this.podNaponom = false;
    }

    public VrstaPogona getVrstaPogona() {
        return vrstaPogona;
    }
}
