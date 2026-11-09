module co.edu.uniquindio.poo.parcial3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.poo.parcial3 to javafx.fxml;
    opens co.edu.uniquindio.poo.parcial3.viewController to javafx.fxml;

    exports co.edu.uniquindio.poo.parcial3;
    exports co.edu.uniquindio.poo.parcial3.model;
    exports co.edu.uniquindio.poo.parcial3.controller;
    exports co.edu.uniquindio.poo.parcial3.viewController;
}
