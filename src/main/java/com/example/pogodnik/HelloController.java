package com.example.pogodnik;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;


public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text pressure;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    void onHelloButtonClick(ActionEvent event) {

    }


    @FXML
    void initialize() {
        // При нажатии на кнопку
        getData.setOnAction(event -> {
            // Получаем данные из текстового поля
            String getUserCity = city.getText().trim();

            if(!getUserCity.equals("")) { // Если данные не пустые
                // Получаем данные о погоде с сайта openweathermap
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=d6bd60d335cc600ec236bfa9253a21d2&units=metric");

                if (!output.isEmpty()) { // Нет ошибки и такой город есть
                    JSONObject obj = new JSONObject(output);
                    // Обрабатываем JSON и устанавливаем данные в текстовые надписи
                    temp_info.setText("Температура: " + obj.getJSONObject("main").getDouble("temp"));
                    temp_feels.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like"));
                    temp_max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max"));
                    temp_min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min"));
                    pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
                }
            }
        });
    }

    // Обработка URL адреса и получение данных с него
    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");

            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Погода для этого города отсутствует на сайте");

            alert.showAndWait();
            // System.out.println("City is not found!");
        }
        return content.toString();
    }

    }



