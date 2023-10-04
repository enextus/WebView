package org.image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://www.google.com");

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(webView);

        Scene scene = new Scene(borderPane, 1024, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simple Web Browser");
        primaryStage.show();
    }
}
