package org.unibl.etf.kompozicije.vagoni;

public class PutnickiVagonSpavanje extends PutnickiVagon {
    private int brMjesta;

    public PutnickiVagonSpavanje(int duzina, int oznaka, int brMjesta) {
        super(duzina, oznaka);
        this.brMjesta = brMjesta;
    }
}
