package ru.itis.kpfu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class BattleController {
    private static final Logger log = LoggerFactory.getLogger(BattleController.class);

    @FXML
    private Label hpText1;

    public void initialize(URL url, ResourceBundle rb) {
        hpText1.setText("static test");
    }

}
