module com.example.ejemploconcurrencia1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ejemploconcurrencia1 to javafx.fxml;
    opens com.example.ejemploconcurrencia1.controller to javafx.fxml;
    exports com.example.ejemploconcurrencia1;
    exports com.example.ejemploconcurrencia1.controller to javafx.fxml;
}