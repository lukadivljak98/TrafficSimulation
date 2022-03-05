package org.unibl.etf.kompozicije.lokomotive;

import org.unibl.etf.kompozicije.lokomotive.interfejsi.prevoziTeretneKompozicije;
import org.unibl.etf.kompozicije.lokomotive.interfejsi.prevoziPutnickeKompozicije;

public class UniverzalnaLokomotiva extends Lokomotiva implements prevoziPutnickeKompozicije, prevoziTeretneKompozicije{

    public UniverzalnaLokomotiva(int id, int snaga, VrstaPogona vrstaPogona) {
        super(id, snaga, vrstaPogona);
    }
}
