module gangulwar.hideit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens gangulwar.hideit to javafx.fxml;
    exports gangulwar.hideit;
}