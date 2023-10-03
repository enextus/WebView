package org.image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        webView.getEngine().load("https://openai.com"); // Укажите URL вашей веб-страницы

        Scene scene = new Scene(webView, 1080, 1024);
        primaryStage.setTitle("My WebView App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
