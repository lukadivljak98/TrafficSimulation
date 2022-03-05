package org.unibl.etf.kompozicije.vagoni;

import org.unibl.etf.kompozicije.lokomotive.interfejsi.prevoziTeretneKompozicije;

public class TeretniVagon extends Vagon implements prevoziTeretneKompozicije {
    private int maxNosivost;

    public TeretniVagon(int duzina, int oznaka, int maxNosivost) {
        super(duzina, oznaka);
        this.maxNosivost = maxNosivost;
    }
}
