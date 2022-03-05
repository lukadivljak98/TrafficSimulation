package org.unibl.etf.mapa;

import org.unibl.etf.kompozicije.Kompozicija;
import org.unibl.etf.vozila.Vozilo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Polje implements Serializable {
    private int x;
    private int y;
    public Element element;
    public static List<Kompozicija> kompozicijeWait = new ArrayList<>();
    public List<Vozilo> vozilaWait = new ArrayList<>();
    private AtomicInteger counter = new AtomicInteger(0);


    public Polje(int x, int y){
        this.element = null;
        this.x = x;
        this.y = y;
    }

    public static void setKompozicija(Kompozicija kompozicija){
        if(kompozicija.getTrenutnaStanica().equals("A")){
            kompozicija.setX(2);
            kompozicija.setY(27);
            for(Element e : kompozicija.elementi){
                e.setX(2);
                e.setY(27);
            }
            ZeljeznickaStanica.kompozicijeA.add(kompozicija);
        }else if(kompozicija.getTrenutnaStanica().equals("B")){
            if(kompozicija.getOdredisnaStanica().equals("A")) {
                kompozicija.setX(6);
                kompozicija.setY(6);
                for(Element e : kompozicija.elementi){
                    e.setX(6);
                    e.setY(6);
                }
            }else {
                kompozicija.setX(7);
                kompozicija.setY(6);
                for(Element e : kompozicija.elementi){
                    e.setX(7);
                    e.setY(6);
                }
            }
            ZeljeznickaStanica.kompozicijeB.add(kompozicija);
        }
        else if(kompozicija.getTrenutnaStanica().equals("C")){
            if(kompozicija.getOdredisnaStanica().equals("A") || kompozicija.getOdredisnaStanica().equals("B")) {
                kompozicija.setX(19);
                kompozicija.setY(12);
                for(Element e : kompozicija.elementi){
                    e.setX(19);
                    e.setY(12);
                }
            } else if(kompozicija.getOdredisnaStanica().equals("D")) {
                kompozicija.setX(20);
                kompozicija.setY(12);
                for(Element e : kompozicija.elementi){
                    e.setX(20);
                    e.setY(12);
                }
            }else if(kompozicija.getOdredisnaStanica().equals("E")){
                kompozicija.setX(20);
                kompozicija.setY(13);
                for(Element e : kompozicija.elementi){
                    e.setX(20);
                    e.setY(13);
                }
            }
            ZeljeznickaStanica.kompozicijeC.add(kompozicija);
        }
        else if(kompozicija.getTrenutnaStanica().equals("D")){
            kompozicija.setX(26);
            kompozicija.setY(1);
            for(Element e : kompozicija.elementi){
                e.setX(26);
                e.setY(1);
            }
            ZeljeznickaStanica.kompozicijeD.add(kompozicija);
        }
        else if(kompozicija.getTrenutnaStanica().equals("E")){
            kompozicija.setX(26);
            kompozicija.setY(25);
            for(Element e : kompozicija.elementi){
                e.setX(26);
                e.setY(25);
            }
            ZeljeznickaStanica.kompozicijeE.add(kompozicija);
        }
    }

    public static void initializeKomposition(Kompozicija kompozicija) {
        if (kompozicija.getPolaznaStanica().equals("A")) {
            kompozicija.setX(2);
            kompozicija.setY(27);
            for(Element e : kompozicija.elementi){
                e.setX(2);
                e.setY(27);
            }
            ZeljeznickaStanica.kompozicijeA.add(kompozicija);
        } else if (kompozicija.getPolaznaStanica().equals("B")) {
            if (kompozicija.getOdredisnaStanica().equals("A")) {
                kompozicija.setX(6);
                kompozicija.setY(6);
                for(Element e : kompozicija.elementi){
                    e.setX(6);
                    e.setY(6);
                }
            } else {
                kompozicija.setX(7);
                kompozicija.setY(6);
                for(Element e : kompozicija.elementi){
                    e.setX(7);
                    e.setY(6);
                }
            }
            ZeljeznickaStanica.kompozicijeB.add(kompozicija);
        } else if (kompozicija.getPolaznaStanica().equals("C")) {
            if (kompozicija.getOdredisnaStanica().equals("A") || kompozicija.getOdredisnaStanica().equals("B")) {
                kompozicija.setX(19);
                kompozicija.setY(12);
                for(Element e : kompozicija.elementi){
                    e.setX(19);
                    e.setY(12);
                }
            } else if (kompozicija.getOdredisnaStanica().equals("D")) {
                kompozicija.setX(20);
                kompozicija.setY(12);
                for(Element e : kompozicija.elementi){
                    e.setX(20);
                    e.setY(12);
                }
            } else if (kompozicija.getOdredisnaStanica().equals("E")) {
                kompozicija.setX(20);
                kompozicija.setY(13);
                for(Element e : kompozicija.elementi){
                    e.setX(20);
                    e.setY(13);
                }
            }
            ZeljeznickaStanica.kompozicijeC.add(kompozicija);
        } else if (kompozicija.getPolaznaStanica().equals("D")) {
            kompozicija.setX(26);
            kompozicija.setY(1);
            for(Element e : kompozicija.elementi){
                e.setX(26);
                e.setY(1);
            }
            ZeljeznickaStanica.kompozicijeD.add(kompozicija);
        } else if (kompozicija.getPolaznaStanica().equals("E")) {
            kompozicija.setX(26);
            kompozicija.setY(25);
            for(Element e : kompozicija.elementi){
                e.setX(26);
                e.setY(25);
            }
            ZeljeznickaStanica.kompozicijeE.add(kompozicija);
        }
    }

    public String getPruzniPrelaz(){
        String s = "";
        if(this.getX() == 2 && this.getY() == 20)
            s = "AB1";
        else if(this.getX() == 2 && this.getY() == 21)
            s = "AB0";
        else if(this.getX() == 13 && this.getY() == 6)
            s = "BC0";
        else if(this.getX() == 14 && this.getY() == 6)
            s = "BC1";
        else if(this.getX() == 26 && this.getY() == 21)
            s = "CE1";
        else if(this.getX() == 26 && this.getY() == 20)
            s = "CE0";
        return s;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
