package org.unibl.etf.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.unibl.etf.kompozicije.Kompozicija;
import org.unibl.etf.mapa.Mapa;
import org.unibl.etf.mapa.Polje;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatistikaController extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        primaryStage.setTitle("Pregled Statistickih Podataka");
        BorderPane borderPane = new BorderPane();
        ComboBox<Kompozicija> comboBox = new ComboBox<>();
        TilePane tilePane = new TilePane(Orientation.VERTICAL);
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);
        borderPane.setMargin(vBox, new Insets(10, 0, 10,20));

        for(int i = 0; i < 30; i++){
            for(int j = 0; j < 30; j++){
                Pane cell = new Pane();
                cell.setStyle("-fx-background-color: white;");
                cell.setPrefSize(30, 30);
                cell.prefHeightProperty().bind(gridPane.heightProperty());
                cell.prefWidthProperty().bind(gridPane.widthProperty());
                cell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(0.5))));
                gridPane.add(cell, i, j);
            }
        }

        gridPane.setAlignment(Pos.CENTER);
        borderPane.setMargin(gridPane, new Insets(30,40,30,40));

        comboBox.setPromptText("Odaberite kompoziciju");

        for(int i = 0; i < Mapa.kompozicijeKraj.size(); i++){
            comboBox.getItems().add(Mapa.kompozicijeKraj.get(i));
        }

        borderPane.setMargin(comboBox, new Insets(0,0,0,50));

        comboBox.setOnAction(event -> {
            tilePane.getChildren().removeAll();
            tilePane.getChildren().clear();
            resetColor(gridPane);


            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./kretanja/Kompozicija" + comboBox.getValue().getId() + ".ser"));
                Kompozicija kompozicija = (Kompozicija) ois.readObject();

                for (Polje p : kompozicija.istorijaPolja) {
                    getPaneFromGridpane(p.getX(), p.getY(), gridPane).setStyle("-fx-background-color: green;");
                }

                String s = "Posjecene stanice: ";
                for (String stanica : kompozicija.posjeceneStanice) {
                    s += stanica;
                    s += ", ";
                }
                Label vrijemeLbl = new Label("Vrijeme kretanja: " + kompozicija.getVrijemeKretanja()/1000 + "s");
                Label staniceLbl = new Label(s);
                vrijemeLbl.setStyle("-fx-font-size: 16;");
                staniceLbl.setStyle("-fx-font-size: 16;");
                tilePane.getChildren().addAll(staniceLbl, vrijemeLbl);

                ois.close();
            } catch (Exception e){
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        });

        vBox.getChildren().addAll(comboBox, tilePane);
        borderPane.setLeft(vBox);
        borderPane.setCenter(gridPane);

        primaryStage.setScene(new Scene(borderPane, 640, 480));
        primaryStage.show();
    }

    public Pane getPaneFromGridpane(int kolona, int red, GridPane gridPane){
        return (Pane) gridPane.getChildrenUnmodifiable().get(kolona * Main.getDimenzija() + red);
    }

    public void resetColor(GridPane gridPane){
        for(int i = 0; i < 30; i++){
            for(int j = 0; j < 30; j++){
                getPaneFromGridpane(i, j, gridPane).setStyle("-fx-background-color: white;");
            }
        }
    }

}
