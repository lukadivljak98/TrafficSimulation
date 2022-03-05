package org.unibl.etf.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.unibl.etf.filewatcher.FileWatcher;
import org.unibl.etf.filewatcher.FileWatcherKompozicije;
import org.unibl.etf.kompozicije.KompozicijaFactory;
import org.unibl.etf.logger.MyLogger;
import org.unibl.etf.mapa.*;
import org.unibl.etf.vozila.VozilaFactory;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;


public class Main extends Application {
    static {
        try {
        MyLogger.setup();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private static final GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mapa Zeljeznicki Saobracaj");
        BorderPane borderPane = new BorderPane();

        VBox vBox = new VBox(30);
        Button startButton = new Button("Start");
        Button statistikaButton = new Button("Statistika");
        TilePane tilePane = new TilePane(Orientation.VERTICAL);
        tilePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1.0))));

        Label autoLabel = new Label("Automobil");
        autoLabel.setStyle("-fx-text-fill: red;");
        Label kamionLabel = new Label("Kamion");
        kamionLabel.setStyle("-fx-text-fill: yellow;");
        Label lokomotivaLabel = new Label("Lokomotiva");
        lokomotivaLabel.setStyle("-fx-text-fill: darkGreen;");
        Label vagonLabel = new Label("Vagon");
        vagonLabel.setStyle("-fx-text-fill: lightGreen;");

        tilePane.getChildren().addAll(autoLabel, kamionLabel, lokomotivaLabel, vagonLabel);
        tilePane.setMargin(autoLabel, new Insets(0,5,5,5));
        tilePane.setMargin(kamionLabel, new Insets(0,5,5,5));
        tilePane.setMargin(lokomotivaLabel, new Insets(0,5,0,5));
        tilePane.setMargin(vagonLabel, new Insets(5,5,0,5));
        tilePane.setAlignment(Pos.CENTER);

        startButton.setOnAction(actionEvent -> {
            new Thread(new FileWatcher()).start();
            new Thread(new VozilaFactory()).start();
            new Thread(new FileWatcherKompozicije()).start();
            new ZeljeznickaStanica();
            new Thread(new KompozicijaFactory()).start();
        });

        statistikaButton.setOnAction(actionEvent -> {
            Stage statistickiPodaciStage = new Stage();
            StatistikaController statistickiPodaci = new StatistikaController();
            try{
                statistickiPodaci.start(statistickiPodaciStage);
            }catch (Exception e){
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        });

        vBox.getChildren().addAll(startButton, statistikaButton, tilePane);
        vBox.setAlignment(Pos.TOP_CENTER);
        borderPane.setLeft(vBox);
        borderPane.setMargin(vBox, new Insets(30,0,10,10));

        gridPane.setAlignment(Pos.CENTER);

        nacrtajMatricu();

        borderPane.setCenter(gridPane);
        borderPane.setMargin(gridPane, new Insets(10,10,10,10));

        primaryStage.setScene(new Scene(borderPane, 1000, 800));
        primaryStage.show();

        primaryStage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });

    }

    private static final int DIMENZIJA = 30;

    public static void main(String[] args) {
        launch(args);
    }

    public void nacrtajMatricu() {
        for(int i = 0; i < DIMENZIJA; i++){
            for(int j = 0; j < DIMENZIJA; j++){
                Pane cell = new Pane();
                cell.setStyle("-fx-background-color: white;");
                cell.setPrefSize(30, 30);
                cell.prefHeightProperty().bind(gridPane.heightProperty());
                cell.prefWidthProperty().bind(gridPane.widthProperty());
                cell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(0.5))));
                gridPane.add(cell, i, j);
                Mapa.matrix[i][j] = new Polje(i, j);
            }
        }
        Mapa.nacrtajPruge();
        Mapa.nacrtajPuteve();
        Mapa.nacrtajPrelaze();
        Mapa.nacrtajStanice();
    }

    public static int getDimenzija(){
        return DIMENZIJA;
    }

    public static GridPane getGridPane() {
        return gridPane;
    }
}
