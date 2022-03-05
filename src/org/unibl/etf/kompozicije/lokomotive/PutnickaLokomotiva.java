package org.unibl.etf.kompozicije.lokomotive;

import org.unibl.etf.kompozicije.lokomotive.interfejsi.prevoziPutnickeKompozicije;

public class PutnickaLokomotiva extends Lokomotiva implements prevoziPutnickeKompozicije{
    public PutnickaLokomotiva(int id, int snaga, VrstaPogona vrstaPogona) {
        super(id, snaga, vrstaPogona);
    }
}
