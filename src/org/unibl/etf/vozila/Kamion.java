package org.unibl.etf.vozila;

public class Kamion extends Vozilo{

    private double nosivost;

    public Kamion() { super(); }

    public Kamion(String marka, String model, int godiste, long brzina, int smijer, double nosivost, int put){
        super(marka, model, godiste, brzina, smijer, put);
        this.nosivost = nosivost;
    }

    @Override
    public String toString(){
        return this.marka + " " + this.model + " " + this.godiste + " " + this.brzina + " " + this.smijer + " " + this.nosivost +
                " " + this.put + " "
                + "\n";
    }
}
