package org.unibl.etf.kompozicije.vagoni;

public class PutnickiVagonSjediste extends PutnickiVagon{
    private int brMjesta;

    public PutnickiVagonSjediste(int duzina, int oznaka, int brMjesta) {
        super(duzina, oznaka);
        this.brMjesta = brMjesta;
    }
}
