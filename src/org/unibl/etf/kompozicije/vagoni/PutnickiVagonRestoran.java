package org.unibl.etf.kompozicije.vagoni;

public class PutnickiVagonRestoran extends PutnickiVagon{
    private String opis;

    public PutnickiVagonRestoran(int duzina, int oznaka, String opis) {
        super(duzina, oznaka);
        this.opis = opis;
    }
}
