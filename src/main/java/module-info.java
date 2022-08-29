module com.example.pogodnik {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.pogodnik to javafx.fxml;
    exports com.example.pogodnik;
}