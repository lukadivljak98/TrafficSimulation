package org.unibl.etf.vozila;

import org.unibl.etf.logger.MyLogger;
import org.unibl.etf.mapa.Mapa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VozilaFactory extends Thread{

    static {
        try {
            MyLogger.setup();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static AtomicInteger brNapravljenihPut1 = new AtomicInteger(0);
    private static AtomicInteger brNapravljenihPut2 = new AtomicInteger(0);
    private static AtomicInteger brNapravljenihPut3 = new AtomicInteger(0);
    private int brNovihPut1;
    private int brNovihPut2;
    private int brNovihPut3;
    private int br1;
    private int br2;
    private int br3;

    public VozilaFactory() {
        brNovihPut1 = Integer.parseInt(ucitajPropertiesPut1()) - brNapravljenihPut1.intValue();
        brNovihPut2 = Integer.parseInt(ucitajPropertiesPut2()) - brNapravljenihPut2.intValue();
        brNovihPut3 = Integer.parseInt(ucitajPropertiesPut3()) - brNapravljenihPut3.intValue();
        br1 = 0;
        br2 = 0;
        br3 = 0;
    }

    public void run(){

            while (br1 < brNovihPut1 || br2 < brNovihPut2 || br3 < brNovihPut3) {
                if (br1 < brNovihPut1) {
                    generisiVozilo(1);
                    br1++;
                    brNapravljenihPut1.incrementAndGet();
                }
                if (br2 < brNovihPut2) {
                    generisiVozilo(2);
                    br2++;
                    brNapravljenihPut2.incrementAndGet();
                }
                if (br3 < brNovihPut3) {
                    generisiVozilo(3);
                    br3++;
                    brNapravljenihPut3.incrementAndGet();
                }
                spavaj();
                if(br1 == brNovihPut1 && br2 == brNovihPut2 && br3 == brNovihPut3)
                    break;
            }
    }

    public void spavaj(){
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public static synchronized void generisiVozilo(int put){
        try{
            String marka;
            String model;
            int godiste;
            int brzina;
            int smijer;
            int maxBrzina;

            Random rand = new Random();

            BufferedReader inMarke = new BufferedReader(new FileReader("./src/org/unibl/etf/vozila/Marke.txt"));
            BufferedReader inModeli = new BufferedReader(new FileReader("./src/org/unibl/etf/vozila/Modeli.txt"));
            ArrayList<String> marke = new ArrayList<>();
            ArrayList<String> modeli = new ArrayList<>();

            while((marka = inMarke.readLine()) != null) marke.add(marka);
            while((model = inModeli.readLine()) != null) modeli.add(model);


            marka = marke.get(rand.nextInt(10));
            model = modeli.get(rand.nextInt(10));
            godiste = rand.nextInt(22) + 1980;
            maxBrzina = getMaxBrzinu(put);
            smijer = rand.nextInt(2);

            do{
                brzina = rand.nextInt(3000) + 100;
            }while(brzina > maxBrzina);

            if (rand.nextInt(2) == 0) {
                int brVrata = rand.nextInt(5) + 3;
                Auto auto = new Auto(marka, model, godiste, brzina, smijer, brVrata, put);
                if(put == 1 && smijer == 0)
                    Mapa.vozilaPut1Smijer0.add(auto);
                else if(put == 1 && smijer == 1)
                    Mapa.vozilaPut1Smijer1.add(auto);
                else if(put == 2 && smijer == 0)
                    Mapa.vozilaPut2Smijer0.add(auto);
                else if(put == 2 && smijer == 1)
                    Mapa.vozilaPut2Smijer1.add(auto);
                else if(put == 3 && smijer == 0)
                    Mapa.vozilaPut3Smijer0.add(auto);
                else if(put == 3 && smijer == 1)
                    Mapa.vozilaPut3Smijer1.add(auto);
                Thread autoThread = new Thread(auto);
                autoThread.start();
            } else {
                double nosivost = 500.0 + (10000.0 - 500.0) * rand.nextDouble();
                Kamion kamion = new Kamion(marka, model, godiste, brzina, smijer, nosivost, put);
                if(put == 1 && smijer == 0)
                    Mapa.vozilaPut1Smijer0.add(kamion);
                else if(put == 1 && smijer == 1)
                    Mapa.vozilaPut1Smijer1.add(kamion);
                else if(put == 2 && smijer == 0)
                    Mapa.vozilaPut2Smijer0.add(kamion);
                else if(put == 2 && smijer == 1)
                    Mapa.vozilaPut2Smijer1.add(kamion);
                else if(put == 3 && smijer == 0)
                    Mapa.vozilaPut3Smijer0.add(kamion);
                else if(put == 3 && smijer == 1)
                    Mapa.vozilaPut3Smijer1.add(kamion);
                Thread kamionThread = new Thread(kamion);
                kamionThread.start();
            }

            inMarke.close();
            inModeli.close();
        }catch (IOException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }

    }

    public static synchronized int getMaxBrzinu(int put){
        String br = null;
        try{
            FileInputStream propFile = new FileInputStream( "./src/org/unibl/etf/filewatcher/Config.properties");
            Properties p = new Properties(System.getProperties());
            p.load(propFile);
            if(put == 1){
                br = p.getProperty("MAXBRZINA_PUTLIJEVO");
            }else if(put == 2){
                br = p.getProperty("MAXBRZINA_PUTSREDINA");
            }else {
                br = p.getProperty("MAXBRZINA_PUTDESNO");
            }
        }catch (IOException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        assert br != null;
        return Integer.parseInt(br);
    }

    public static synchronized String ucitajPropertiesPut1(){
        String br = null;
        try{
            FileInputStream propFile = new FileInputStream( "./src/org/unibl/etf/filewatcher/Config.properties");
            Properties p = new Properties(System.getProperties());
            p.load(propFile);
            br = p.getProperty("BRVOZILA_PUTLIJEVO");
        }catch (IOException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        return br;
    }

    public static synchronized String ucitajPropertiesPut2(){
        String br = null;
        try{
            FileInputStream propFile = new FileInputStream( "./src/org/unibl/etf/filewatcher/Config.properties");
            Properties p = new Properties(System.getProperties());
            p.load(propFile);
            br = p.getProperty("BRVOZILA_PUTSREDINA");
        }catch (IOException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        return br;
    }

    public static synchronized String ucitajPropertiesPut3(){
        String br = null;
        try{
            FileInputStream propFile = new FileInputStream( "./src/org/unibl/etf/filewatcher/Config.properties");
            Properties p = new Properties(System.getProperties());
            p.load(propFile);
            br = p.getProperty("BRVOZILA_PUTDESNO");
        }catch (IOException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        return br;
    }
}
