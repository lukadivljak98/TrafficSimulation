package org.unibl.etf.kompozicije.vagoni;

import org.unibl.etf.mapa.Element;

public abstract class Vagon extends Element {
    private int duzina;
    private int oznaka;
    public boolean podNaponom;

    public Vagon(int duzina, int oznaka){
        this.duzina = duzina;
        this.oznaka = oznaka;
        this.podNaponom = false;
    }
}
