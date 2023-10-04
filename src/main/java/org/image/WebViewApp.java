package org.image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebViewApp extends Application {
    private static final Logger logger = LoggerFactory.getLogger(WebViewApp.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting application");

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://www.google.com");
        logger.info("WebEngine loaded google.com");

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(webView);

        Scene scene = new Scene(borderPane, 1024, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simple Web Browser");
        primaryStage.show();

        logger.info("Application started");
    }

}
