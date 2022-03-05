package org.unibl.etf.kompozicije.lokomotive;

import org.unibl.etf.kompozicije.lokomotive.interfejsi.odrzavanjePruga;

public class ManevarskaLokomotiva extends Lokomotiva implements odrzavanjePruga{

    public ManevarskaLokomotiva(int id, int snaga, VrstaPogona vrstaPogona) {
        super(id, snaga, vrstaPogona);
    }
}
