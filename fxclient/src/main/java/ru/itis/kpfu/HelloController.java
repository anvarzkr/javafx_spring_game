package ru.itis.kpfu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.chainsaw.Main;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private TextField usernameField;


    public void sayHello() throws Exception {

        log.info("Button clicked");

        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    URI uri = new URIBuilder("http://localhost:8090/new_client").addParameter("username", usernameField.getText()).build();
                    HttpGet httpGet = new HttpGet(uri);
                    log.debug("Http request sent to  " + httpGet.getURI());
                    CloseableHttpResponse response1 = httpclient.execute(httpGet);
                    BufferedReader rd = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
                    StringBuffer result = new StringBuffer();
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    log.debug(result.toString());

                    try {
                        MainApp.mainAppStatic.gameId = Integer.parseInt(result.toString());
                        MainApp.mainAppStatic.playerId = (MainApp.mainAppStatic.gameId < 0) ? 2 : 1;

                        if (MainApp.mainAppStatic.gameId < 0)
                            MainApp.mainAppStatic.gameId *= -1;

                    }catch (NumberFormatException nfe){
                        nfe.printStackTrace();
                        log.debug("CANT GET GAME TOKEN");
                    }

                    MainApp.mainAppStatic.replaceSceneContent("battlefield", "battlefield");

                    MainApp.mainAppStatic.heartbeatTimer = FxTimer.runPeriodically(
                            Duration.ofMillis(2000),
                            () -> makeHeartbeat());

                    MainApp.mainAppStatic.stage.getScene().addEventFilter(KeyEvent.KEY_RELEASED, event -> {
                        log.info("Pressed: "+event.getCode());
                        switch (event.getCode()) {
                            case Q:
                                log.info("You are trying to ATTACK");
                                abilityRequest("ATTACK");
                                break;
                            case W:
                                log.info("You are trying to HARD ATTACK");
                                abilityRequest("HEAVY_ATTACK");
                                break;
                            case E:
                                log.info("You are trying to BLOCK");
                                abilityRequest("BLOCK");
                                break;
                            default:
                                log.info("Theres no listener for that key");
                                break;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void abilityRequest(String ability){
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URI uri = new URIBuilder("http://localhost:8090/set_ability")
                    .addParameter("ability", ability)
                    .addParameter("player_id", MainApp.mainAppStatic.playerId + "")
                    .addParameter("game_id", MainApp.mainAppStatic.gameId + "")
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            log.debug("Http request sent to  " + httpGet.getURI());
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            log.debug(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void makeHeartbeat(){
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URI uri = new URIBuilder("http://localhost:8090/heartbeat")
                    .addParameter("player_id", MainApp.mainAppStatic.playerId + "")
                    .addParameter("game_id", MainApp.mainAppStatic.gameId + "")
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            log.debug("Http request sent to  " + httpGet.getURI());
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            log.debug("Damage dealt to this player: " + result.toString());

            int damageDealt = 0;

            try {
                damageDealt = Integer.parseInt(result.toString());
                if (damageDealt == -1){
                    Stage dialog = new Stage();
                    dialog.initStyle(StageStyle.UTILITY);
                    String alertText;

                    log.debug("PLAYER 1 WON THE GAME");

                    if (MainApp.mainAppStatic.playerId == 1){
                        alertText = "YOU WON THE GAME!";
                    }else{
                        alertText = "YOU LOST THE GAME!";
                    }

                    Scene scene = new Scene(new Group(new Text(25, 25, "Your current HP: " + MainApp.mainAppStatic.currentHealth)));
                    dialog.setScene(scene);
                    dialog.show();
                }else if (damageDealt == -2){
                    Stage dialog = new Stage();
                    dialog.initStyle(StageStyle.UTILITY);
                    String alertText;

                    log.debug("PLAYER 2 WON THE GAME");

                    if (MainApp.mainAppStatic.playerId == 2){
                        alertText = "YOU WON THE GAME!";
                    }else{
                        alertText = "YOU LOST THE GAME!";
                    }

                    Scene scene = new Scene(new Group(new Text(25, 25, "Your current HP: " + MainApp.mainAppStatic.currentHealth)));
                    dialog.setScene(scene);
                    dialog.show();
                }
            }catch (NumberFormatException nfe){
                nfe.printStackTrace();
            }

            MainApp.mainAppStatic.currentHealth -= damageDealt > 0 ? damageDealt : 0;
            log.info("Current health: " + MainApp.mainAppStatic.currentHealth);

//            if (hpText1 != null)
//                hpText1.setText(MainApp.mainAppStatic.currentHealth + "");

            if (damageDealt > 0){
                Stage dialog = new Stage();
                dialog.initStyle(StageStyle.UTILITY);
                Scene scene = new Scene(new Group(new Text(25, 25, "Your current HP: " + MainApp.mainAppStatic.currentHealth)));
                dialog.setScene(scene);
                dialog.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
