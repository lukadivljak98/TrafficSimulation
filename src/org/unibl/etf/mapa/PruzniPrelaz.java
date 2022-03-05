package org.unibl.etf.mapa;

import org.unibl.etf.vozila.Vozilo;

import java.util.ArrayList;
import java.util.List;

public class PruzniPrelaz extends Polje{

    public static List<Vozilo> waitingListAB0 = new ArrayList<>();
    public static List<Vozilo> waitingListAB1 = new ArrayList<>();
    public static List<Vozilo> waitingListBC0 = new ArrayList<>();
    public static List<Vozilo> waitingListBC1 = new ArrayList<>();
    public static List<Vozilo> waitingListCE0 = new ArrayList<>();
    public static List<Vozilo> waitingListCE1 = new ArrayList<>();

    public PruzniPrelaz(int x, int y){
        super(x, y);
        setX(x);
        setY(y);
    }
}
