package org.unibl.etf.mapa;

import org.unibl.etf.kompozicije.Kompozicija;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ZeljeznickaStanica {
    public static List<Kompozicija> kompozicijeA = new ArrayList<>();
    public static List<Kompozicija> kompozicijeB = new ArrayList<>();
    public static List<Kompozicija> kompozicijeC = new ArrayList<>();
    public static List<Kompozicija> kompozicijeD = new ArrayList<>();
    public static List<Kompozicija> kompozicijeE = new ArrayList<>();

    public static HashMap<String, AtomicInteger> hashMapA = new HashMap<>();
    public static HashMap<String, AtomicInteger> hashMapB = new HashMap<>();
    public static HashMap<String, AtomicInteger> hashMapC = new HashMap<>();
    public static HashMap<String, AtomicInteger> hashMapD = new HashMap<>();
    public static HashMap<String, AtomicInteger> hashMapE = new HashMap<>();

    public ZeljeznickaStanica(){
        hashMapA.put("B", new AtomicInteger(0));
        hashMapB.put("A", new AtomicInteger(0));
        hashMapB.put("C", new AtomicInteger(0));
        hashMapC.put("B", new AtomicInteger(0));
        hashMapC.put("E", new AtomicInteger(0));
        hashMapC.put("D", new AtomicInteger(0));
        hashMapD.put("C", new AtomicInteger(0));
        hashMapE.put("C", new AtomicInteger(0));
    }

}