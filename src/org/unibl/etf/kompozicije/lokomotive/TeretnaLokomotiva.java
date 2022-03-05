package org.unibl.etf.kompozicije.lokomotive;

import org.unibl.etf.kompozicije.lokomotive.interfejsi.prevoziTeretneKompozicije;

public class TeretnaLokomotiva extends Lokomotiva implements prevoziTeretneKompozicije{

    public TeretnaLokomotiva(int id, int snaga, VrstaPogona vrstaPogona) {
        super(id, snaga, vrstaPogona);
    }
}
