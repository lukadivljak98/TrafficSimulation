package org.unibl.etf.vozila;

public class Auto extends Vozilo{

    private int brVrata;

    public Auto() { super(); }

    public Auto(String marka, String model, int godiste, long brzina, int smijer, int brVrata, int put){
        super(marka, model, godiste, brzina, smijer, put);
        this.brVrata = brVrata;
    }

    @Override
    public String toString(){
        return this.marka + " " + this.model + " " + this.godiste + " " + this.brzina + " " + this.smijer + " " + this.brVrata +
                " " + this.put + " "
                + "\n";
    }
}
