package org.unibl.etf.kompozicije.vagoni;

import org.unibl.etf.kompozicije.lokomotive.interfejsi.prevoziPutnickeKompozicije;

public abstract class PutnickiVagon extends Vagon implements prevoziPutnickeKompozicije {

    public PutnickiVagon(int duzina, int oznaka) {
        super(duzina, oznaka);
    }
}
