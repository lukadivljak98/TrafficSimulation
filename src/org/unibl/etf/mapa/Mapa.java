package org.unibl.etf.mapa;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.unibl.etf.gui.Main;

import javafx.scene.image.Image;
import org.unibl.etf.kompozicije.Kompozicija;
import org.unibl.etf.kompozicije.PodNaponom;
import org.unibl.etf.kompozicije.lokomotive.Lokomotiva;
import org.unibl.etf.kompozicije.vagoni.Vagon;
import org.unibl.etf.logger.MyLogger;
import org.unibl.etf.vozila.Auto;
import org.unibl.etf.vozila.Kamion;
import org.unibl.etf.vozila.Vozilo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mapa {

    static {
        try {
            MyLogger.setup();
        }
        catch (IOException e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public static final Polje[][] matrix = new Polje[Main.getDimenzija()][Main.getDimenzija()];
    public static List<Vozilo> vozilaPut1Smijer0 = new ArrayList<>();
    public static List<Vozilo> vozilaPut1Smijer1 = new ArrayList<>();
    public static List<Vozilo> vozilaPut2Smijer0 = new ArrayList<>();
    public static List<Vozilo> vozilaPut2Smijer1 = new ArrayList<>();
    public static List<Vozilo> vozilaPut3Smijer0 = new ArrayList<>();
    public static List<Vozilo> vozilaPut3Smijer1 = new ArrayList<>();
    public static List<Kompozicija> sveKompozicije = new ArrayList<>();
    public static List<Kompozicija> kompozicijeKraj = new ArrayList<>();

    public static void nacrtajPruge() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("./src/org/unibl/etf/mapa/MAPA.txt"));
            String s;
            while(!(s = in.readLine()).equals("PUT")){
                String[] parametri = s.split(",");
                boolean caseOne = false; //npr. 2-29
                boolean caseTwo = false; //npr. 21-26,18
                boolean caseThree = false; //npr. 19,7-11
                int a1 = 0, b1 = 0, c1 = 0, a2 = 0, b2 = 0, c2 = 0;
                if(parametri[0].contains("-")){
                    String[] parametriPom = parametri[0].split("-");
                    a1 = Integer.parseInt(parametriPom[0]);
                    b1 = Integer.parseInt(parametriPom[1]);
                    caseTwo = true;
                } else {
                    c1 = Integer.parseInt(parametri[0]);
                }
                if(parametri[1].contains("-")){
                    String[] parametriPom = parametri[1].split("-");
                    a2 = Integer.parseInt(parametriPom[0]);
                    b2 = Integer.parseInt(parametriPom[1]);
                    caseThree = true;
                } else {
                    c2 = Integer.parseInt(parametri[1]);
                }
                if(!caseTwo && !caseThree) caseOne = true;
                if(caseOne) obojiOdDo(c1, c2);
                if(caseTwo) obojiOdDoPom(a1, b1, c2);
                if(caseThree) obojiOdDoPomPom(c1, a2, b2);
            }
            in.close();
        }catch(IOException | InterruptedException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public static Pane getPaneFromGridpane(int kolona, int red){
        return (Pane) Main.getGridPane().getChildrenUnmodifiable().get(kolona * Main.getDimenzija() + red);
    }

    public static void paintCell(int x, int y, String color){
        getPaneFromGridpane(x, y).setStyle("-fx-background-color: " + color + ";");
    }

    public static void paintBorderGreen(int x, int y){
        getPaneFromGridpane(x, y).setStyle("-fx-border-color: red; -fx-background-color: green;");
    }

    public static void paintBorderLightGreen(int x, int y){
        getPaneFromGridpane(x, y).setStyle("-fx-border-color: red; -fx-background-color: lightGreen;");
    }

    public static void paintBorder(int x, int y){
        getPaneFromGridpane(x, y).setStyle("-fx-border-color: red; -fx-background-color: lightGrey;");
    }

    public static void obojiOdDo(int x, int y) throws InterruptedException {
        Mapa.matrix[x][y] = new PrugaCell(x, y);
        setElement(matrix[x][y], null);
    }

    public static void obojiOdDoPrelaz(int x, int y) throws InterruptedException {
        Mapa.matrix[x][y] = new PruzniPrelaz(x, y);
        setElement(matrix[x][y], null);
    }

    public static void obojiOdDoStanica(int x, int y) throws FileNotFoundException, InterruptedException {
        Image image = new Image(new FileInputStream("./src/org/unibl/etf/mapa/train.png"));
        Mapa.matrix[x][y] = new StanicaCell(x, y);
        setElement(matrix[x][y], null);
        if(x == 1 && y == 28){
            Label stanicaAlbl = new Label(" A");
            stanicaAlbl.setStyle("-fx-font-weight: bold;");
            getPaneFromGridpane(x, y).getChildren().add(stanicaAlbl);
        }
        if(x == 2 && y == 27){
            addImageToPane(x, y, image);
        }
        if(x == 6 && y == 6){
            Label stanicaBlbl = new Label(" B");
            stanicaBlbl.setStyle("-fx-font-weight: bold;");
            getPaneFromGridpane(x, y).getChildren().add(stanicaBlbl);
        }
        if(x == 7 && y == 5){
            addImageToPane(x, y, image);
        }
        if(x == 19 && y == 13){
            Label stanicaClbl = new Label(" C");
            stanicaClbl.setStyle("-fx-font-weight: bold;");
            getPaneFromGridpane(x, y).getChildren().add(stanicaClbl);
        }
        if(x == 20 && y == 12){
            addImageToPane(x, y, image);
        }
        if(x == 26 && y == 2){
            Label stanicaDlbl = new Label(" D");
            stanicaDlbl.setStyle("-fx-font-weight: bold;");
            getPaneFromGridpane(x, y).getChildren().add(stanicaDlbl);
        }
        if(x == 27 && y == 1){
            addImageToPane(x, y, image);
        }
        if(x == 25 && y == 26){
            Label stanicaElbl = new Label(" E");
            stanicaElbl.setStyle("-fx-font-weight: bold;");
            getPaneFromGridpane(x, y).getChildren().add(stanicaElbl);
        }
        if(x == 26 && y == 25){
            addImageToPane(x, y, image);
        }
    }

    public static void addImageToPane(int x, int y, Image image){
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        imageView.setPreserveRatio(true);
        getPaneFromGridpane(x, y).getChildren().add(imageView);
    }

    public static void obojiOdDoPom(int a, int b, int c) throws InterruptedException {
        for(int i = a; i <= b; i++){
            Mapa.matrix[i][c] = new PrugaCell(i, c);
            setElement(matrix[i][c], null);
        }
    }

    public static void obojiOdDoPomPut(int a, int b, int c) throws InterruptedException {
        for(int i = a; i <= b; i++){
            Mapa.matrix[i][c] = new PutCell(i, c);
            setElement(matrix[i][c], null);
        }
    }

    public static void obojiOdDoPomPom(int a, int b, int c) throws InterruptedException {
        for(int i = b; i <= c; i++){
            Mapa.matrix[a][i] = new PrugaCell(a, i);
            setElement(matrix[a][i], null);
        }
    }

    public static void obojiOdDoPomPomPut(int a, int b, int c) throws InterruptedException {
        for(int i = b; i <= c; i++){
            Mapa.matrix[a][i] = new PutCell(a, i);
            setElement(matrix[a][i], null);
        }
    }

    public static void nacrtajPuteve(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("./src/org/unibl/etf/mapa/MAPA.txt"));
            String s;
            while(!(s = in.readLine()).equals("PUT")) {}
            while(!(s = in.readLine()).equals("PRELAZ")){
                if(s.equals("PUT")) continue;
                String[] parametri = s.split(",");
                boolean caseTwo = false;
                boolean caseThree = false;
                int a1 = 0, b1 = 0, c1 = 0, a2 = 0, b2 = 0, c2 = 0;
                if(parametri[0].contains("-")){
                    String[] parametriPom = parametri[0].split("-");
                    a1 = Integer.parseInt(parametriPom[0]);
                    b1 = Integer.parseInt(parametriPom[1]);
                    caseTwo = true;
                } else {
                    c1 = Integer.parseInt(parametri[0]);
                }
                if(parametri[1].contains("-")){
                    String[] parametriPom = parametri[1].split("-");
                    a2 = Integer.parseInt(parametriPom[0]);
                    b2 = Integer.parseInt(parametriPom[1]);
                    caseThree = true;
                } else {
                    c2 = Integer.parseInt(parametri[1]);
                }
                if(caseTwo) obojiOdDoPomPut(a1, b1, c2);
                if(caseThree) obojiOdDoPomPomPut(c1, a2, b2);
            }
            in.close();
        }catch(IOException | InterruptedException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public static void nacrtajPrelaze(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("./src/org/unibl/etf/mapa/MAPA.txt"));
            String s;
            while (!(s = in.readLine()).equals("PRELAZ")) {}
            while(!(s = in.readLine()).equals("STANICA")) {
                if(s.equals("PRELAZ")) continue;
                String[] parametri = s.split(",");
                int a = Integer.parseInt(parametri[0]);
                int b = Integer.parseInt(parametri[1]);
                obojiOdDoPrelaz(a, b);
            }
            in.close();
        }catch (IOException | InterruptedException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public static void nacrtajStanice(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("./src/org/unibl/etf/mapa/MAPA.txt"));
            String s;
            while (!(s = in.readLine()).equals("STANICA")) {}
            while(!(s = in.readLine()).equals("EOF")) {
                if(s.equals("STANICA")) continue;
                String[] parametri = s.split(",");
                int a = Integer.parseInt(parametri[0]);
                int b = Integer.parseInt(parametri[1]);
                obojiOdDoStanica(a, b);
            }
            in.close();
        }catch (IOException | InterruptedException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public static void setElement(Polje polje, Element element) throws InterruptedException {
        synchronized (matrix) {
            polje.element = element;
            if (element instanceof Auto) {
                paintCell(element.getX(), element.getY(), "red");
            } else if (element instanceof Kamion) {
                paintCell(element.getX(), element.getY(), "yellow");
            } else if(element instanceof PodNaponom){
                paintBorder(element.getX(), element.getY());
            }
            else if (element instanceof Lokomotiva){
                if(((Lokomotiva) element).podNaponom){
                    paintBorderGreen(element.getX(), element.getY());
                }else paintCell(element.getX(), element.getY(), "green");
            } else if (element instanceof Vagon){
                if(((Vagon) element).podNaponom){
                    paintBorderLightGreen(element.getX(), element.getY());
                } else paintCell(element.getX(), element.getY(), "lightGreen");
            } else if(element == null){
                if(polje instanceof PrugaCell) {
                    paintCell(polje.getX(), polje.getY(), "lightGrey");
                    if(!Polje.kompozicijeWait.isEmpty()) {
                        synchronized (Polje.kompozicijeWait.get(0)) {
                            Polje.kompozicijeWait.get(0).notify();
                            Polje.kompozicijeWait.remove(0);
                        }
                    }
                }
                else if(polje instanceof PruzniPrelaz) {
                    paintCell(polje.getX(), polje.getY(), "black");
                    if(!Polje.kompozicijeWait.isEmpty()) {
                        synchronized (Polje.kompozicijeWait.get(0)) {
                            Polje.kompozicijeWait.get(0).notify();
                            Polje.kompozicijeWait.remove(0);
                        }
                    }
                }
                else if(polje instanceof StanicaCell) {
                    paintCell(polje.getX(), polje.getY(), "grey");
                    if(!Polje.kompozicijeWait.isEmpty()) {
                        synchronized (Polje.kompozicijeWait.get(0)) {
                            Polje.kompozicijeWait.get(0).notify();
                            Polje.kompozicijeWait.remove(0);
                        }
                    }
                }
                else if(polje instanceof PutCell){
                    paintCell(polje.getX(), polje.getY(), "lightBlue");
                    if(!polje.vozilaWait.isEmpty()) {
                        synchronized (polje.vozilaWait.get(0)) {
                            polje.vozilaWait.get(0).notify();
                            polje.vozilaWait.remove(0);
                        }
                    }
                }
            }
        }
    }

    public static void setElementPom(Polje polje, Element element) {
        synchronized (matrix) {
            if(polje.element == null)
                polje.element = element;
            if (element instanceof PodNaponom) {
                paintBorder(polje.getX(), polje.getY());
            }
             else if (element instanceof Lokomotiva) {
                if (((Lokomotiva) element).podNaponom) {
                    paintBorderGreen(polje.getX(), polje.getY());
                }else{
                    paintCell(polje.getX(), polje.getY(), "green");
                }
            } else if (element instanceof Vagon) {
                if (((Vagon) element).podNaponom) {
                    paintBorderLightGreen(polje.getX(), polje.getY());
                }else {
                    paintCell(polje.getX(), polje.getY(), "lightGreen");
                }
            } else if (element == null) {
                paintCell(polje.getX(), polje.getY(), "lightGrey");
            }
        }
    }

}
