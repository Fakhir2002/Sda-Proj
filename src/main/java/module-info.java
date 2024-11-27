module Controllers {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;


    exports Controllers;
    opens Controllers to javafx.fxml;
    exports OOP;
    opens OOP to javafx.fxml;
    exports Application;
    opens Application to javafx.fxml;
}