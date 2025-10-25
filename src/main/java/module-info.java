module com.example.clickthecirclegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.clickthecirclegame to javafx.fxml;
    exports com.example.clickthecirclegame;
}