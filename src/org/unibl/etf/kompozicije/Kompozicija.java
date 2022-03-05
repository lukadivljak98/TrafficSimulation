package org.unibl.etf.kompozicije;

import org.unibl.etf.kompozicije.lokomotive.Lokomotiva;
import org.unibl.etf.kompozicije.lokomotive.VrstaPogona;
import org.unibl.etf.kompozicije.vagoni.Vagon;
import org.unibl.etf.logger.MyLogger;
import org.unibl.etf.mapa.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kompozicija extends Element implements Runnable{

    static {
        try {
            MyLogger.setup();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int brLokomotiva;
    private int brVagona;
    private int brzina;
    public ArrayList<Vagon> vagoni = new ArrayList<>();
    public ArrayList<Lokomotiva> lokomotive = new ArrayList<>();
    private String polaznaStanica;
    private String odredisnaStanica;
    public List<Element> elementi = new ArrayList<>();
    public List<Element> elementiPom = new ArrayList<>();
    private String trenutnaStanica = "";
    private int brojac = 0;
    public List<Polje> istorijaPolja = new ArrayList<>();
    public List<String> posjeceneStanice = new ArrayList<>();
    private static int br = 0;
    private int id;
    private long pocetnoVrijeme;
    private long krajnjeVrijeme;
    private long vrijemeKretanja;
    private int brojacVrijeme = 0;

    private boolean zapocniKretanje = false;
    private boolean izaslaIzStanice = false;
    private boolean uslaUStanicu = false;
    private boolean trebaUparkirati = false;
    private boolean stiglaNaOdrediste = false;

    private int brojacZaE = 0;

    public boolean isElektricna = false;

    public Kompozicija(){}

    public Kompozicija(int brLokomotiva, int brVagona, int brzina, String polaznaStanica, String odredisnaStanica, ArrayList<Vagon> vagoni,
                       ArrayList<Lokomotiva> lokomotive){
        this.brLokomotiva = brLokomotiva;
        this.brVagona = brVagona;
        this.brzina = brzina;
        this.polaznaStanica = polaznaStanica;
        this.odredisnaStanica = odredisnaStanica;
        this.lokomotive = lokomotive;
        this.vagoni = vagoni;
        this.id = br++;
    }

    @Override
    public void run() {
        napraviElemente(this.lokomotive, this.vagoni);
        if(!this.getPolaznaStanica().equals("-1A") || !this.getPolaznaStanica().equals("-1E")) {
            this.posjeceneStanice.add(this.polaznaStanica);
        }
        while(!stiglaNaOdrediste) {
            if(this.brojac == 0) {
                Polje.initializeKomposition(this);
                trenutnaStanica = StanicaCell.getStanica(this.getX(), this.getY());
            }
            else {
                trenutnaStanica = StanicaCell.getStanica(this.getX(), this.getY());
                Polje.setKompozicija(this);
            }

            if (trenutnaStanica.equals("A")) {
                if (ZeljeznickaStanica.kompozicijeA.indexOf(this) != 0) {
                    synchronized (this){
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                        }
                    }
                }
                } else if (trenutnaStanica.equals("B")) {
                    if (ZeljeznickaStanica.kompozicijeB.indexOf(this) != 0) {
                        synchronized (this){
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                            }
                        }
                    }
                } else if (trenutnaStanica.equals("C")) {
                    if (ZeljeznickaStanica.kompozicijeC.indexOf(this) != 0) {
                        synchronized (this){
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                            }
                        }
                    }
                } else if (trenutnaStanica.equals("D")) {
                    if (ZeljeznickaStanica.kompozicijeD.indexOf(this) != 0) {
                        synchronized (this){
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                            }
                        }
                    }
                } else if (trenutnaStanica.equals("E")) {
                    if (ZeljeznickaStanica.kompozicijeE.indexOf(this) != 0) {
                        synchronized (this){
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                            }
                        }
                    }
                }
                else this.zapocniKretanje = true;

            try {
                if(this.getPolaznaStanica().equals("-1A")) {
                    ispodA();
                    this.polaznaStanica = "A";
                    this.posjeceneStanice.add("A");
                    Polje.initializeKomposition(this);
                    trenutnaStanica = StanicaCell.getStanica(this.getX(), this.getY());
                }
                if(this.getPolaznaStanica().equals("-1E")){
                    desnoOdE();
                    this.polaznaStanica = "E";
                    this.posjeceneStanice.add("E");
                    Polje.initializeKomposition(this);
                    trenutnaStanica = StanicaCell.getStanica(this.getX(), this.getY());
                }
                if(brojacVrijeme == 0) {
                    pocetnoVrijeme = System.currentTimeMillis();
                    brojacVrijeme++;
                }
                kretanje();
            } catch (InterruptedException e) {
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
            }
            trenutnaStanica = StanicaCell.getStanica(this.getX(), this.getY());
            this.posjeceneStanice.add(trenutnaStanica);
            if(!trenutnaStanica.equals(this.odredisnaStanica)){
                this.brojac++;
                this.uslaUStanicu = false;
                this.trebaUparkirati = false;
                this.izaslaIzStanice = false;
                this.zapocniKretanje = false;
                for(Element e : elementiPom)
                    e.uStanici = false;
                this.elementiPom.clear();
            }else this.stiglaNaOdrediste = true;
        }

        Mapa.kompozicijeKraj.add(this);
        krajnjeVrijeme = System.currentTimeMillis();
        vrijemeKretanja = krajnjeVrijeme - pocetnoVrijeme;
        try {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./kretanja/Kompozicija" + this.id + ".ser"));
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public void kretanje() throws InterruptedException {

            if(this.trenutnaStanica.equals("A")) {
                if(ZeljeznickaStanica.hashMapB.get("A").intValue() != 0) {
                    synchronized (this) {
                        wait();
                    }
                }
                ZeljeznickaStanica.hashMapA.get("B").incrementAndGet();
                Mapa.matrix[2][20].getCounter().incrementAndGet();
                Mapa.matrix[2][21].getCounter().incrementAndGet();
            }else if(this.trenutnaStanica.equals("B")){
                if(this.getNextStation().equals("A")) {
                    if (ZeljeznickaStanica.hashMapA.get("B").intValue() != 0) {
                        synchronized (this){
                            wait();
                        }
                    }
                    ZeljeznickaStanica.hashMapB.get("A").incrementAndGet();
                    Mapa.matrix[2][20].getCounter().incrementAndGet();
                    Mapa.matrix[2][21].getCounter().incrementAndGet();
                }
                else {
                    if(ZeljeznickaStanica.hashMapC.get("B").intValue() != 0){
                        synchronized (this){
                            wait();
                        }
                    }
                    ZeljeznickaStanica.hashMapB.get("C").incrementAndGet();
                    Mapa.matrix[13][6].getCounter().incrementAndGet();
                    Mapa.matrix[14][6].getCounter().incrementAndGet();
                }
            }
            else if(this.trenutnaStanica.equals("C")){
                if(this.getNextStation().equals("B")) {
                    if(ZeljeznickaStanica.hashMapB.get("C").intValue() != 0){
                        synchronized (this){
                            wait();
                        }
                    }
                    ZeljeznickaStanica.hashMapC.get("B").incrementAndGet();
                    Mapa.matrix[13][6].getCounter().incrementAndGet();
                    Mapa.matrix[14][6].getCounter().incrementAndGet();
                }
                else if (this.getNextStation().equals("D")) {
                    if(ZeljeznickaStanica.hashMapD.get("C").intValue() != 0){
                        synchronized (this){
                            wait();
                        }
                    }
                    ZeljeznickaStanica.hashMapC.get("D").incrementAndGet();
                }
                else {
                    if(ZeljeznickaStanica.hashMapE.get("C").intValue() != 0){
                        synchronized (this){
                            wait();
                        }
                    }
                    ZeljeznickaStanica.hashMapC.get("E").incrementAndGet();
                    Mapa.matrix[26][21].getCounter().incrementAndGet();
                    Mapa.matrix[26][20].getCounter().incrementAndGet();
                }
            }
            else if(this.trenutnaStanica.equals("D")){
                if(ZeljeznickaStanica.hashMapC.get("D").intValue() != 0){
                    synchronized (this){
                        wait();
                    }
                }
                ZeljeznickaStanica.hashMapD.get("C").incrementAndGet();
            }
            else if(this.trenutnaStanica.equals("E")){
                if(ZeljeznickaStanica.hashMapC.get("E").intValue() != 0){
                    synchronized (this){
                        wait();
                    }
                }
                ZeljeznickaStanica.hashMapE.get("C").incrementAndGet();
                Mapa.matrix[26][21].getCounter().incrementAndGet();
                Mapa.matrix[26][20].getCounter().incrementAndGet();
            }

            isparkiraj(this);
            this.izaslaIzStanice = true;
            izbaciIzListe(this);

        String s = "";
        while(!this.trebaUparkirati) {

            for (int i = elementiPom.size() - 1; i >= 0; i--) {
                s = whereIsPrugaCell(elementiPom.get(0));
                if (s.equals("uparkiraj")) {
                    this.trebaUparkirati = true;
                    break;
                }
                if(s.equals("goreCekaj") || s.equals("desnoCekaj") || s.equals("doleCekaj") || s.equals("lijevoCekaj")) {
                    synchronized (this) {
                        if (s.equals("goreCekaj"))
                            Polje.kompozicijeWait.add(this);
                        else if (s.equals("desnoCekaj"))
                            Polje.kompozicijeWait.add(this);
                        else if (s.equals("doleCekaj"))
                            Polje.kompozicijeWait.add(this);
                        else if (s.equals("lijevoCekaj"))
                            Polje.kompozicijeWait.add(this);
                        wait();
                    }
                }
                s = whereIsPrugaCell(elementiPom.get(0));
                if (i != 0) {
                    if (i == elementiPom.size() - 1) {
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], null);
                        if(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()] instanceof PruzniPrelaz){
                            oslobodiPrelaz(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                        }
                    }
                    Element elementIspred = elementiPom.get(i - 1);
                    elementiPom.get(i).setX(elementIspred.getX());
                    elementiPom.get(i).setY(elementIspred.getY());
                    Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                } else {
                    if (s.equals("gore")) {
                        elementiPom.get(i).decrementY();
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    } else if (s.equals("dole")) {
                        elementiPom.get(i).incrementY();
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    } else if (s.equals("desno")) {
                        elementiPom.get(i).incrementX();
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    } else if (s.equals("lijevo")) {
                        elementiPom.get(i).decrementX();
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    }
                }
            }
            if (!this.trebaUparkirati)
                spavaj();
        }
        while(!uslaUStanicu){
            uparkiraj();
            inkrementujUStanicu();
            this.setX(elementiPom.get(0).getX());
            this.setY(elementiPom.get(0).getY());
            this.uslaUStanicu = true;
            this.trenutnaStanica = StanicaCell.getStanica(this.getX(), this.getY());
            if(this.trenutnaStanica.equals("A")){
                ZeljeznickaStanica.hashMapB.get("A").decrementAndGet();
                if(ZeljeznickaStanica.hashMapB.get("A").intValue() == 0){
                    if(!ZeljeznickaStanica.kompozicijeA.isEmpty() && ZeljeznickaStanica.kompozicijeA.get(0).getNextStation().equals("B")) {
                        synchronized (ZeljeznickaStanica.kompozicijeA.get(0)) {
                            ZeljeznickaStanica.kompozicijeA.get(0).notify();
                        }
                    }
                }
            }
            else if(this.trenutnaStanica.equals("B")){
                if(this.getPreviousStation().equals("A")) {
                    ZeljeznickaStanica.hashMapA.get("B").decrementAndGet();
                    if(ZeljeznickaStanica.hashMapA.get("B").intValue() == 0){
                        if(!ZeljeznickaStanica.kompozicijeB.isEmpty() && ZeljeznickaStanica.kompozicijeB.get(0).getNextStation().equals("A")) {
                            synchronized (ZeljeznickaStanica.kompozicijeB.get(0)) {
                                ZeljeznickaStanica.kompozicijeB.get(0).notify();
                            }
                        }
                    }
                }
                else {
                    ZeljeznickaStanica.hashMapC.get("B").decrementAndGet();
                    if(ZeljeznickaStanica.hashMapC.get("B").intValue() == 0){
                        if(!ZeljeznickaStanica.kompozicijeB.isEmpty() && ZeljeznickaStanica.kompozicijeB.get(0).getNextStation().equals("C")) {
                            synchronized (ZeljeznickaStanica.kompozicijeB.get(0)) {
                                ZeljeznickaStanica.kompozicijeB.get(0).notify();
                            }
                        }
                    }
                }
            }
            else if(this.trenutnaStanica.equals("C")){
                if(this.getPreviousStation().equals("B")){
                    ZeljeznickaStanica.hashMapB.get("C").decrementAndGet();
                    if(ZeljeznickaStanica.hashMapB.get("C").intValue() == 0){
                        if(!ZeljeznickaStanica.kompozicijeC.isEmpty() && ZeljeznickaStanica.kompozicijeC.get(0).getNextStation().equals("B")) {
                            synchronized (ZeljeznickaStanica.kompozicijeC.get(0)) {
                                ZeljeznickaStanica.kompozicijeC.get(0).notify();
                            }
                        }
                    }
                }else if(this.getPreviousStation().equals("D")){
                    ZeljeznickaStanica.hashMapD.get("C").decrementAndGet();
                    if(ZeljeznickaStanica.hashMapD.get("C").intValue() == 0){
                        if(!ZeljeznickaStanica.kompozicijeC.isEmpty() && ZeljeznickaStanica.kompozicijeC.get(0).getNextStation().equals("D")) {
                            synchronized (ZeljeznickaStanica.kompozicijeC.get(0)) {
                                ZeljeznickaStanica.kompozicijeC.get(0).notify();
                            }
                        }
                    }
                }
                else{
                    ZeljeznickaStanica.hashMapE.get("C").decrementAndGet();
                    if(ZeljeznickaStanica.hashMapE.get("C").intValue() == 0){
                        if(!ZeljeznickaStanica.kompozicijeC.isEmpty() && ZeljeznickaStanica.kompozicijeC.get(0).getNextStation().equals("E")) {
                            synchronized (ZeljeznickaStanica.kompozicijeC.get(0)) {
                                ZeljeznickaStanica.kompozicijeC.get(0).notify();
                            }
                        }
                    }
                }
            }
            else if(this.trenutnaStanica.equals("D")){
                ZeljeznickaStanica.hashMapC.get("D").decrementAndGet();
                if(ZeljeznickaStanica.hashMapC.get("D").intValue() == 0){
                    if(!ZeljeznickaStanica.kompozicijeD.isEmpty() && ZeljeznickaStanica.kompozicijeD.get(0).getNextStation().equals("C")) {
                        synchronized (ZeljeznickaStanica.kompozicijeD.get(0)) {
                            ZeljeznickaStanica.kompozicijeD.get(0).notify();
                        }
                    }
                }
            }
            else if(this.trenutnaStanica.equals("E")){
                if(!this.getPolaznaStanica().equals("-1E"))
                    ZeljeznickaStanica.hashMapC.get("E").decrementAndGet();
                if(ZeljeznickaStanica.hashMapC.get("E").intValue() == 0){
                    if(!ZeljeznickaStanica.kompozicijeE.isEmpty() && ZeljeznickaStanica.kompozicijeE.get(0).getNextStation().equals("C")) {
                        synchronized (ZeljeznickaStanica.kompozicijeE.get(0)) {
                            ZeljeznickaStanica.kompozicijeE.get(0).notify();
                        }
                    }
                }
            }
        }
    }

    public void isparkiraj(Kompozicija kompozicija) throws InterruptedException {
        String s = "";
        for(Element e : kompozicija.elementi){
            elementiPom.add(e);

            for(int i = elementiPom.size()-1; i >= 0; i--) {
                s = whereIsPrugaCell(elementiPom.get(0));
                if(s.equals("goreCekaj") || s.equals("desnoCekaj") || s.equals("doleCekaj") || s.equals("lijevoCekaj")) {
                    synchronized (this) {
                        if (s.equals("goreCekaj"))
                            Polje.kompozicijeWait.add(this);
                        else if (s.equals("desnoCekaj"))
                            Polje.kompozicijeWait.add(this);
                        else if (s.equals("doleCekaj"))
                            Polje.kompozicijeWait.add(this);
                        else if (s.equals("lijevoCekaj"))
                            Polje.kompozicijeWait.add(this);
                        wait();
                    }
                }
                s = whereIsPrugaCell(elementiPom.get(0));
                if (i != 0) {
                    Element elementIspred = elementiPom.get(i - 1);
                    elementiPom.get(i).setX(elementIspred.getX());
                    elementiPom.get(i).setY(elementIspred.getY());
                    Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                } else {
                    if (s.equals("gore")) {
                        elementiPom.get(i).decrementY();
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    } else if (s.equals("dole")) {
                        elementiPom.get(i).incrementY();
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    } else if (s.equals("desno")) {
                        elementiPom.get(i).incrementX();
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    } else if (s.equals("lijevo")) {
                        elementiPom.get(i).decrementX();
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    }
                }
            }
            spavaj();
        }
    }

    public void uparkiraj() throws InterruptedException {
        String s = "";
        int brProlaza = 0;
        while(brProlaza <= elementiPom.size()){
            s = whereIsStanicaCell(elementiPom.get(0));
            for(int i = elementiPom.size()-1; i >= 0; i--) {

                if(elementiPom.get(i).uStanici){
                    continue;
                }
                elementiPom.get(brProlaza).uStanici = true;
                if (i != 0) {
                    if(i == elementiPom.size() - 1) {
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], null);
                        if(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()] instanceof PruzniPrelaz){
                            oslobodiPrelaz(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                        }
                    }
                    Element elementIspred = elementiPom.get(i - 1);
                    elementiPom.get(i).setX(elementIspred.getX());
                    elementiPom.get(i).setY(elementIspred.getY());
                    if(!elementiPom.get(i).uStanici)
                        Mapa.setElement(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()], elementiPom.get(i));
                } else {
                    if (s.equals("gore")) {
                        elementiPom.get(i).decrementY();
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    } else if (s.equals("dole")) {
                        elementiPom.get(i).incrementY();
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    } else if (s.equals("desno")) {
                        elementiPom.get(i).incrementX();
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    } else if (s.equals("lijevo")) {
                        elementiPom.get(i).decrementX();
                        this.istorijaPolja.add(Mapa.matrix[elementiPom.get(i).getX()][elementiPom.get(i).getY()]);
                    }
                }
            }
            brProlaza++;
            spavaj();
        }
    }

    public void napraviElemente(ArrayList<Lokomotiva> lokomotive, ArrayList<Vagon> vagoni){
        for(Lokomotiva l : lokomotive) {
            if(l.getVrstaPogona() == VrstaPogona.ELEKTRICNA)
                this.isElektricna = true;
        }
        if(this.isElektricna)
            this.elementi.add(new PodNaponom());
        for(Lokomotiva l : lokomotive)
            this.elementi.add(l);
        for(Vagon v : vagoni)
            this.elementi.add(v);
        if(this.isElektricna){
            for(Lokomotiva l : lokomotive)
                l.podNaponom = true;
            for(Vagon v : vagoni)
                v.podNaponom = true;
            this.elementi.add(new PodNaponom());
        }
    }

    public String whereIsPrugaCell(Element element){
        String s = "";
        if((Mapa.matrix[element.getX()][element.getY()-1] instanceof PrugaCell || Mapa.matrix[element.getX()][element.getY()-1] instanceof PruzniPrelaz)
                && Mapa.matrix[element.getX()][element.getY()-1].element == null) {
            s += "gore";
        }
        else if((Mapa.matrix[element.getX()][element.getY()+1] instanceof PrugaCell || Mapa.matrix[element.getX()][element.getY()+1] instanceof PruzniPrelaz)
                && Mapa.matrix[element.getX()][element.getY()+1].element == null) {
            s += "dole";
        }
        else if((Mapa.matrix[element.getX()+1][element.getY()] instanceof PrugaCell || Mapa.matrix[element.getX()+1][element.getY()] instanceof PruzniPrelaz)
                && Mapa.matrix[element.getX()+1][element.getY()].element == null) {
            if(!this.getTrenutnaStanica().equals("E"))
                s += "desno";
        }
        else if((Mapa.matrix[element.getX()-1][element.getY()] instanceof PrugaCell || Mapa.matrix[element.getX()-1][element.getY()] instanceof PruzniPrelaz)
                && Mapa.matrix[element.getX()-1][element.getY()].element == null) {
            s += "lijevo";
        }
        else if((Mapa.matrix[element.getX()][element.getY()-1] instanceof PrugaCell || Mapa.matrix[element.getX()][element.getY()-1] instanceof PruzniPrelaz)
                && Mapa.matrix[element.getX()][element.getY()-1].element != null
                && !this.elementiPom.contains(Mapa.matrix[element.getX()][element.getY()-1].element)) {
            s += "goreCekaj";
        }
        else if((Mapa.matrix[element.getX()][element.getY()+1] instanceof PrugaCell || Mapa.matrix[element.getX()][element.getY()+1] instanceof PruzniPrelaz)
                && Mapa.matrix[element.getX()][element.getY()+1].element != null
                && !this.elementiPom.contains(Mapa.matrix[element.getX()][element.getY()+1].element)) {
            s += "doleCekaj";
        }
        else if((Mapa.matrix[element.getX()+1][element.getY()] instanceof PrugaCell || Mapa.matrix[element.getX()+1][element.getY()] instanceof PruzniPrelaz)
                && Mapa.matrix[element.getX()+1][element.getY()].element != null
                && !this.elementiPom.contains(Mapa.matrix[element.getX()+1][element.getY()].element)) {
            s += "desnoCekaj";
        }
        else if((Mapa.matrix[element.getX()-1][element.getY()] instanceof PrugaCell || Mapa.matrix[element.getX()-1][element.getY()] instanceof PruzniPrelaz)
                && Mapa.matrix[element.getX()-1][element.getY()].element != null
                && !this.elementiPom.contains(Mapa.matrix[element.getX()-1][element.getY()].element)) {
            s += "lijevoCekaj";
        }
        else if((Mapa.matrix[element.getX()][element.getY()-1] instanceof StanicaCell && !(StanicaCell.getStanica(element.getX(),element.getY()-1)).equals(this.getPolaznaStanica()))
                || (Mapa.matrix[element.getX()][element.getY()+1] instanceof StanicaCell && !(StanicaCell.getStanica(element.getX(),element.getY()+1)).equals(this.getPolaznaStanica()))
                || (Mapa.matrix[element.getX()-1][element.getY()] instanceof StanicaCell && !(StanicaCell.getStanica(element.getX()-1,element.getY())).equals(this.getPolaznaStanica()))
                || (Mapa.matrix[element.getX()+1][element.getY()] instanceof StanicaCell && !(StanicaCell.getStanica(element.getX()+1,element.getY())).equals(this.getPolaznaStanica()))){
            s += "uparkiraj";
        }
        else {
            s += "nema";
        }
        return s;
    }

    public String whereIsStanicaCell(Element element){
        String s = "";
        if(Mapa.matrix[element.getX()][element.getY()-1] instanceof StanicaCell) {
            s += "gore";
        }
        else if(Mapa.matrix[element.getX()][element.getY()+1] instanceof StanicaCell) {
            s += "dole";
        }
        else if(Mapa.matrix[element.getX()+1][element.getY()] instanceof StanicaCell) {
            s += "desno";
        }
        else if(Mapa.matrix[element.getX()-1][element.getY()] instanceof StanicaCell) {
            s += "lijevo";
        } else {
            s += "nema";
        }
        return s;
    }

    public void spavaj(){
        try{
            Thread.sleep(this.brzina);
        }catch (InterruptedException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public String getPolaznaStanica() {
        return this.polaznaStanica;
    }

    public String getOdredisnaStanica() {
        return this.odredisnaStanica;
    }

    public void inkrementujUStanicu() {
        String s = whereIsStanicaCell(elementiPom.get(0));

        if (s.equals("gore")) {
            elementiPom.get(0).decrementY();
            this.istorijaPolja.add(Mapa.matrix[elementiPom.get(0).getX()][elementiPom.get(0).getY()]);
        } else if (s.equals("dole")) {
            elementiPom.get(0).incrementY();
            this.istorijaPolja.add(Mapa.matrix[elementiPom.get(0).getX()][elementiPom.get(0).getY()]);
        } else if (s.equals("desno")) {
            elementiPom.get(0).incrementX();
            this.istorijaPolja.add(Mapa.matrix[elementiPom.get(0).getX()][elementiPom.get(0).getY()]);
        } else if (s.equals("lijevo")) {
            elementiPom.get(0).decrementX();
            this.istorijaPolja.add(Mapa.matrix[elementiPom.get(0).getX()][elementiPom.get(0).getY()]);
        }
        for(Element e : elementiPom){
            if(elementi.indexOf(e) != 0) {
                e.setX(elementiPom.get(0).getX());
                e.setY(elementiPom.get(0).getY());
            }
        }
    }

    public void izbaciIzListe(Kompozicija kompozicija){
        if (trenutnaStanica.equals("A")) {
            ZeljeznickaStanica.kompozicijeA.remove(kompozicija);
            if(!ZeljeznickaStanica.kompozicijeA.isEmpty()) {
                synchronized (ZeljeznickaStanica.kompozicijeA.get(0)) {
                    ZeljeznickaStanica.kompozicijeA.get(0).notify();
                }
            }
        } else if (trenutnaStanica.equals("B")) {
            ZeljeznickaStanica.kompozicijeB.remove(kompozicija);
            if(!ZeljeznickaStanica.kompozicijeB.isEmpty()) {
                synchronized (ZeljeznickaStanica.kompozicijeB.get(0)) {
                    ZeljeznickaStanica.kompozicijeB.get(0).notify();
                }
            }
        } else if (trenutnaStanica.equals("C")) {
            ZeljeznickaStanica.kompozicijeC.remove(kompozicija);
            if(!ZeljeznickaStanica.kompozicijeC.isEmpty()) {
                synchronized (ZeljeznickaStanica.kompozicijeC.get(0)) {
                    ZeljeznickaStanica.kompozicijeC.get(0).notify();
                }
            }
        } else if (trenutnaStanica.equals("D")) {
            ZeljeznickaStanica.kompozicijeD.remove(kompozicija);
            if(!ZeljeznickaStanica.kompozicijeD.isEmpty()) {
                synchronized (ZeljeznickaStanica.kompozicijeD.get(0)) {
                    ZeljeznickaStanica.kompozicijeD.get(0).notify();
                }
            }
        } else if (trenutnaStanica.equals("E")) {
            ZeljeznickaStanica.kompozicijeE.remove(kompozicija);
            if(!ZeljeznickaStanica.kompozicijeE.isEmpty()) {
                synchronized (ZeljeznickaStanica.kompozicijeE.get(0)) {
                    ZeljeznickaStanica.kompozicijeE.get(0).notify();
                }
            }
        }
    }

    public String getNextStation(){
        String s = "";
        trenutnaStanica = StanicaCell.getStanica(this.getX(), this.getY());
        if(trenutnaStanica.equals("A")){
            s = "B";
        }
        else if(trenutnaStanica.equals("B")){
            if(this.odredisnaStanica.equals("A"))
                s = "A";
            else s = "C";
        }
        else if(trenutnaStanica.equals("C")){
            if(this.odredisnaStanica.equals("A") || this.odredisnaStanica.equals("B"))
                s = "B";
            else if(this.odredisnaStanica.equals("D"))
                s = "D";
            else s = "E";
        }
        else if(trenutnaStanica.equals("D")){
            s = "C";
        }
        else if(trenutnaStanica.equals("E")){
            s = "C";
        }
        return s;
    }

    public String getPreviousStation(){
        String s = "";
        trenutnaStanica = StanicaCell.getStanica(this.getX(), this.getY());
        if(trenutnaStanica.equals("A")){
            s = "B";
        }else if(trenutnaStanica.equals("B")){
            if(this.polaznaStanica.equals("A"))
                s = "A";
            else s = "C";
        }
        else if(trenutnaStanica.equals("C")){
            if(this.polaznaStanica.equals("A") || this.polaznaStanica.equals("B"))
                s = "B";
            else if (this.polaznaStanica.equals("D"))
                s = "D";
            else s = "E";
        }
        else if(trenutnaStanica.equals("D")){
            s = "D";
        }
        else if(trenutnaStanica.equals("E")){
            s = "E";
        }

        return s;
    }

    public String getTrenutnaStanica() {
        return trenutnaStanica;
    }

    public void oslobodiPrelaz(Polje polje){
        if(polje.getPruzniPrelaz().equals("AB0")){
            polje.getCounter().decrementAndGet();
            if(!PruzniPrelaz.waitingListAB0.isEmpty()) {
                synchronized (PruzniPrelaz.waitingListAB0.get(0)) {
                    if(polje.getCounter().intValue() == 0) {
                        PruzniPrelaz.waitingListAB0.get(0).notify();
                        PruzniPrelaz.waitingListAB0.remove(0);
                    }
                }
            }
        }
        else if(polje.getPruzniPrelaz().equals("AB1")) {
            polje.getCounter().decrementAndGet();
            if (!PruzniPrelaz.waitingListAB1.isEmpty()) {
                synchronized (PruzniPrelaz.waitingListAB1.get(0)) {
                    if(polje.getCounter().intValue() == 0) {
                        PruzniPrelaz.waitingListAB1.get(0).notify();
                        PruzniPrelaz.waitingListAB1.remove(0);
                    }
                }
            }
        }
        else if(polje.getPruzniPrelaz().equals("BC0")){
            polje.getCounter().decrementAndGet();
            if(!PruzniPrelaz.waitingListBC0.isEmpty()){
                synchronized (PruzniPrelaz.waitingListBC0.get(0)){
                    if(polje.getCounter().intValue() == 0) {
                        PruzniPrelaz.waitingListBC0.get(0).notify();
                        PruzniPrelaz.waitingListBC0.remove(0);
                    }
                }
            }
        }
        else if(polje.getPruzniPrelaz().equals("BC1")){
            polje.getCounter().decrementAndGet();
            if(!PruzniPrelaz.waitingListBC1.isEmpty()){
                synchronized (PruzniPrelaz.waitingListBC1.get(0)){
                    if(polje.getCounter().intValue() == 0) {
                        PruzniPrelaz.waitingListBC1.get(0).notify();
                        PruzniPrelaz.waitingListBC1.remove(0);
                    }
                }
            }
        }
        else if(polje.getPruzniPrelaz().equals("CE0")){
            polje.getCounter().decrementAndGet();
            if(!PruzniPrelaz.waitingListCE0.isEmpty()){
                synchronized (PruzniPrelaz.waitingListCE0.get(0)){
                    if(polje.getCounter().intValue() == 0) {
                        PruzniPrelaz.waitingListCE0.get(0).notify();
                        PruzniPrelaz.waitingListCE0.remove(0);
                    }
                }
            }
        }
        else if(polje.getPruzniPrelaz().equals("CE1")){
            polje.getCounter().decrementAndGet();
            if(!PruzniPrelaz.waitingListCE1.isEmpty()){
                synchronized (PruzniPrelaz.waitingListCE1.get(0)){
                    if(polje.getCounter().intValue() == 0) {
                        PruzniPrelaz.waitingListCE1.get(0).notify();
                        PruzniPrelaz.waitingListCE1.remove(0);
                    }
                }
            }
        }
    }

    @Override
    public String toString(){
        return "Kompozicija " + this.id;
    }

    public void ispodA() throws InterruptedException {
        for(Element e : this.elementi){
            Mapa.setElementPom(Mapa.matrix[2][29], e);
            spavaj();
        }
        Mapa.setElementPom(Mapa.matrix[2][29], null);
        this.istorijaPolja.add(Mapa.matrix[2][29]);
    }

    public void desnoOdE() throws InterruptedException {
        Mapa.setElementPom(Mapa.matrix[29][25], this.elementi.get(0));
        spavaj();
        Mapa.setElementPom(Mapa.matrix[28][25], this.elementi.get(0));
        Mapa.setElementPom(Mapa.matrix[29][25], this.elementi.get(1));
        spavaj();
        Mapa.setElementPom(Mapa.matrix[27][25], this.elementi.get(0));
        Mapa.setElementPom(Mapa.matrix[28][25], this.elementi.get(1));
        Mapa.setElementPom(Mapa.matrix[29][25], this.elementi.get(2));
        spavaj();

        int br = 1;
        int i = 0;
        while(br < this.elementi.size() - 2){
            Mapa.setElementPom(Mapa.matrix[27][25], this.elementi.get(i+1));
            Mapa.setElementPom(Mapa.matrix[28][25], this.elementi.get(i+2));
            Mapa.setElementPom(Mapa.matrix[29][25], this.elementi.get(i+3));
            spavaj();
            i++;
            br++;
        }

        Mapa.setElementPom(Mapa.matrix[29][25], null);
        Mapa.setElementPom(Mapa.matrix[27][25], this.elementi.get(elementi.size()-2));
        Mapa.setElementPom(Mapa.matrix[28][25], this.elementi.get(elementi.size()-3));
        spavaj();
        Mapa.setElementPom(Mapa.matrix[28][25], null);
        Mapa.setElementPom(Mapa.matrix[27][25], this.elementi.get(elementi.size()-3));
        spavaj();
        Mapa.setElementPom(Mapa.matrix[27][25], null);
        spavaj();

        this.istorijaPolja.add(Mapa.matrix[29][25]);
        this.istorijaPolja.add(Mapa.matrix[28][25]);
        this.istorijaPolja.add(Mapa.matrix[27][25]);
    }

    public long getVrijemeKretanja() {
        return vrijemeKretanja;
    }

    public int getId() {
        return id;
    }
}
