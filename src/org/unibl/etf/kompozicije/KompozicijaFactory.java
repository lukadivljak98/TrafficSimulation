package org.unibl.etf.kompozicije;

import org.unibl.etf.kompozicije.lokomotive.*;
import org.unibl.etf.kompozicije.lokomotive.interfejsi.*;
import org.unibl.etf.kompozicije.vagoni.*;
import org.unibl.etf.logger.MyLogger;
import org.unibl.etf.mapa.Mapa;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KompozicijaFactory extends Thread{

    static {
        try {
            MyLogger.setup();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        generisiKompozicije();
    }

    public synchronized void generisiKompozicije() {
        try {
            final File folder = new File("./src/org/unibl/etf/kompozicije/vozovi/");

            for (final File file : Objects.requireNonNull(folder.listFiles())) {
                    int brLokomotiva;
                    int brVagona;
                    int brzina;
                    ArrayList<Vagon> vagoni = new ArrayList<>();
                    ArrayList<Lokomotiva> lokomotive = new ArrayList<>();
                    String polaznaStanica;
                    String odredisnaStanica;

                    Random rand = new Random();
                    BufferedReader in = new BufferedReader(new FileReader(file));
                    String s = in.readLine();
                    String[] parametri = s.split("#");
                    String[] parametriLokomotiva = parametri[0].split("-");
                    brLokomotiva = parametriLokomotiva.length;
                    String[] parametriVagon = parametri[1].split("-");
                    brVagona = parametriVagon.length;

                    for (String str : parametriLokomotiva) {
                        if (str.equals("L1")) {
                            VrstaPogona vp = VrstaPogona.values()[rand.nextInt(3)];
                            lokomotive.add(new PutnickaLokomotiva(rand.nextInt(1000), rand.nextInt(5000) + 500, vp));
                        }
                        else if (str.equals("L2")) {
                            VrstaPogona vp = VrstaPogona.values()[rand.nextInt(3)];
                            lokomotive.add(new TeretnaLokomotiva(rand.nextInt(1000), rand.nextInt(10000) + 1000, vp));
                        }
                        else if (str.equals("L3")) {
                            VrstaPogona vp = VrstaPogona.values()[rand.nextInt(3)];
                            lokomotive.add(new ManevarskaLokomotiva(rand.nextInt(1000), rand.nextInt(2000) + 100, vp));
                        }
                        else if (str.equals("L4")) {
                            VrstaPogona vp = VrstaPogona.values()[rand.nextInt(3)];
                            lokomotive.add(new UniverzalnaLokomotiva(rand.nextInt(1000), rand.nextInt(5000) + 500, vp));
                        }
                    }
                    if(brVagona != 0) {
                        for (String str : parametriVagon) {
                            if (str.equals("V1"))
                                vagoni.add(new PutnickiVagonLezaj(rand.nextInt(10) + 3, rand.nextInt(1000)));
                            else if (str.equals("V2"))
                                vagoni.add(new PutnickiVagonRestoran(rand.nextInt(10) + 3, rand.nextInt(1000), "Vagon sa restoranom"));
                            else if (str.equals("V3"))
                                vagoni.add(new PutnickiVagonSjediste(rand.nextInt(10) + 3, rand.nextInt(1000), rand.nextInt(80) + 20));
                            else if (str.equals("V4"))
                                vagoni.add(new PutnickiVagonSpavanje(rand.nextInt(10) + 3, rand.nextInt(1000), rand.nextInt(80) + 20));
                            else if (str.equals("V5"))
                                vagoni.add(new TeretniVagon(rand.nextInt(10) + 3, rand.nextInt(1000), rand.nextInt(5000) + 500));
                            else if (str.equals("V6"))
                                vagoni.add(new PosebneNamjeneVagon(rand.nextInt(10) + 3, rand.nextInt(1000)));
                        }
                    }

                    brzina = Integer.parseInt(parametri[2]);
                    if (brzina < 500)
                        brzina = 500;
                    polaznaStanica = parametri[3];
                    odredisnaStanica = parametri[4];

                    boolean provjera = provjeriKompoziciju(lokomotive, vagoni);

                    try {
                        if (provjera && brLokomotiva > 0) {
                            Kompozicija kompozicija = new Kompozicija(brLokomotiva, brVagona, brzina, polaznaStanica, odredisnaStanica, vagoni, lokomotive);
                            Mapa.sveKompozicije.add(kompozicija);
                            new Thread(kompozicija).start();
                        } else {
                            throw new Exception("Nemoguca kompozicija");
                        }
                    } catch (Exception e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    in.close();
                    file.delete();
            }
        }catch (IOException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    private boolean provjeriKompoziciju(ArrayList<Lokomotiva> lokomotive, ArrayList<Vagon> vagoni){
        boolean provjera1;
        boolean provjera2;
        boolean provjera3;

        provjera1 = lokomotive.stream().allMatch(l -> l instanceof prevoziPutnickeKompozicije) &&
                    vagoni.stream().allMatch(v -> v instanceof prevoziPutnickeKompozicije);
        provjera2 = lokomotive.stream().allMatch(l -> l instanceof prevoziTeretneKompozicije) &&
                    vagoni.stream().allMatch(v -> v instanceof prevoziTeretneKompozicije);
        provjera3 = lokomotive.stream().allMatch(l -> l instanceof odrzavanjePruga);

        return provjera1 || provjera2 || provjera3;
    }
}
